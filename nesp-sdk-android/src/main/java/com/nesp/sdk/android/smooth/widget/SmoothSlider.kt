/*
 * Copyright (C) 2021 The NESP Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nesp.sdk.android.smooth.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import com.nesp.sdk.android.R
import com.nesp.sdk.android.core.ktx.content.getColorCompat
import com.nesp.sdk.android.util.AttrUtil
import kotlin.math.max
import kotlin.math.min

/**
 *
 *
 * Team: NESP Technology
 * Author: <a href="mailto:1756404649@qq.com">Jinzhaolu Email:1756404649@qq.com</a>
* Time: Created 2020/10/17 14:31
 * Project: NespAndroidSdk
 **/
class SmoothSlider : View {

    private var mSliderHeight = 0F
    private var mSliderWidth = 0F

    private var mProgressBackgroundPaint: Paint? = null

    @ColorInt
    private var mProgressBackgroundColor: Int? = null

    private var mProgressPaint: Paint? = null

    @ColorInt
    private var mProgressColor: Int? = null
    private var mProgressStartX = 0F
    private var mProgressStopX = 0F
    private var mProgressTopY = 0F
    private var mProgressBottomY = 0F
    private var mProgressHeight = 0F

    /** Position for thumb range of 0 to 1 **/
    private var mThumbPosition = 0F
    private var mThumbPaint: Paint? = null
    private var mThumbOuterPaint: Paint? = null
    private var mThumbRadius = -1F
    private var mThumbElevation = -1F
    private var mThumbColor: Int? = null
    private var mThumbCircleX = 0F
    private var mThumbCircleY = 0F

    private var mTouchX = 0F
    private var mTouchY = 0F

    private var mTouchMode = TOUCH_MODE_IDLE

    private var mMaxProgress: Int = 100
    private var mStepSize: Int = 0

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) :
            this(context, attrs, 0)


    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    ) {

        val typedArray = context.obtainStyledAttributes(
            attrs, R.styleable.SmoothSlider, defStyleAttr,
            AttrUtil.getAttrOutTypeValue(context, R.attr.smoothSliderStyle).resourceId
        )

        val defaultProgress = getProgress()
        val progress = typedArray.getInt(
            R.styleable.SmoothSlider_android_progress, defaultProgress
        )
        mThumbPosition = getThumbPositionFromProgress(progress)

        mMaxProgress = typedArray.getInt(R.styleable.SmoothSlider_android_max, mMaxProgress)

        mStepSize = typedArray.getInt(R.styleable.SmoothSlider_stepSize, mStepSize)

        val defaultProgressHeight = 10F
        mProgressHeight = typedArray.getDimension(
            R.styleable.SmoothSlider_progressHeight, defaultProgressHeight
        )
        if (mProgressHeight <= 0) {
            mProgressHeight = defaultProgressHeight
        }

        mProgressColor = context.getColorCompat(R.color.smoothSystemBlue)
        mProgressColor = typedArray.getColor(
            R.styleable.SmoothSlider_progressColor, mProgressColor!!
        )

        mProgressBackgroundColor = context.getColorCompat(R.color.smoothSystemGray)
        mProgressBackgroundColor = typedArray.getColor(
            R.styleable.SmoothSlider_progressBackgroundColor, mProgressBackgroundColor!!
        )

        val defaultThumbRadius = 45F
        mThumbRadius = typedArray.getDimension(
            R.styleable.SmoothSlider_thumbRadius, defaultThumbRadius
        )
        if (mThumbRadius <= 0) {
            mThumbRadius = defaultThumbRadius
        }

        val defaultThumbElevation = 10F
        mThumbElevation = typedArray.getDimension(
            R.styleable.SmoothSlider_thumbElevation, defaultThumbElevation
        )
        if (mThumbElevation < 0) {
            mThumbElevation = defaultThumbElevation
        }

        mThumbColor = context.getColorCompat(R.color.white)
        mThumbColor = typedArray.getColor(
            R.styleable.SmoothSlider_thumbColor, mThumbColor!!
        )

        typedArray.recycle()

        mProgressBackgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mProgressBackgroundPaint!!.color = mProgressBackgroundColor!!

        mProgressPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mProgressPaint!!.color = mProgressColor!!

        mThumbPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mThumbPaint!!.color = mThumbColor!!
        mThumbPaint!!.setShadowLayer(
            mThumbElevation, 8F, 8F,
            context.getColorCompat(
                AttrUtil.getAttrOutTypeValue(context, R.attr.smoothElevationColor).resourceId
            )
        )
        mThumbOuterPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mThumbOuterPaint!!.style = Paint.Style.STROKE
        mThumbOuterPaint!!.strokeWidth = 2F
        mThumbOuterPaint!!.color =
            context.getColorCompat(R.color.smooth_widget_common_background_color)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mSliderHeight = max(
            max(mProgressHeight, mThumbRadius * 2) + paddingTop + paddingBottom,
            measuredHeight.toFloat()
        )
        mSliderWidth = measuredWidth.toFloat()
        if (mSliderHeight >= mSliderWidth) {
            mSliderHeight = mSliderWidth / 2
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        mProgressStartX = paddingStart.toFloat()
        mProgressStopX = mSliderWidth * mThumbPosition - paddingEnd
        mProgressTopY = paddingTop + (mSliderHeight - mProgressHeight) / 2
        mProgressBottomY = mProgressTopY + mProgressHeight - paddingBottom

        mThumbCircleX = min(
            max(
                mSliderWidth * mThumbPosition,
                mThumbRadius + paddingStart
            ), mSliderWidth - paddingEnd - mThumbRadius
        )

        mThumbCircleY = mProgressBottomY - mProgressHeight / 2
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawBackground(canvas)
        drawProgressLine(canvas)
        drawThumb(canvas)
    }

    private fun drawThumb(canvas: Canvas) {
        canvas.drawCircle(mThumbCircleX, mThumbCircleY, mThumbRadius, mThumbPaint!!)
        canvas.drawCircle(mThumbCircleX, mThumbCircleY, mThumbRadius, mThumbOuterPaint!!)
    }

    private fun drawBackground(canvas: Canvas) {
        canvas.drawRect(
            paddingStart.toFloat(), mProgressTopY, mSliderWidth - paddingEnd,
            mProgressBottomY, mProgressBackgroundPaint!!
        )
    }

    private fun drawProgressLine(canvas: Canvas) {
        canvas.drawRect(
            mProgressStartX, mProgressTopY, mProgressStopX, mProgressBottomY, mProgressPaint!!
        )
    }


    private fun hitThumb(x: Float, y: Float): Boolean {
        return (x in (mThumbCircleX - mThumbRadius)..(mThumbCircleX + mThumbRadius)) &&
                (y in (mThumbCircleY - mThumbRadius)..(mThumbCircleY + mThumbRadius))
    }

    override fun performClick(): Boolean {
        return super.performClick()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (isEnabled && hitThumb(event.x, event.y)) {
                    mTouchMode = TOUCH_MODE_DOWN
                    mTouchX = event.x
                    mTouchY = event.y
                    return true
                }
            }
            MotionEvent.ACTION_MOVE -> {
                when (mTouchMode) {
                    TOUCH_MODE_IDLE -> {

                    }

                    TOUCH_MODE_DOWN -> {
                        mTouchX = event.x
                        mTouchY = event.y
                        mTouchMode = TOUCH_MODE_DRAGGING
                        onSliderChangeListener?.onStartTrackingTouch(this)
                    }

                    TOUCH_MODE_DRAGGING -> {
                        val sliderProgressRange = getSliderProgressRange()
                        var eventX = event.x
                        val newPos: Float
                        if (eventX < (paddingStart + mThumbRadius)) {
                            eventX = 0F
                        } else if (eventX > mSliderWidth - paddingEnd - mThumbRadius) {
                            eventX = sliderProgressRange
                        }
                        newPos = eventX / sliderProgressRange

                        if (mStepSize <= 0) {
                            if (newPos != mThumbPosition) {
                                setThumbPosition(newPos)
                            }
                        } else {
                            val range = arrayListOf<Int>()
                            for (i in 0..mMaxProgress step mStepSize) range.add(i)
                            if ((newPos * mMaxProgress).toInt() in range && newPos != mThumbPosition) {
                                setThumbPosition(newPos)
                            }
                        }
                    }
                }
            }

            MotionEvent.ACTION_UP,
            MotionEvent.ACTION_CANCEL -> {
                mTouchMode = TOUCH_MODE_IDLE
                onSliderChangeListener?.onStopTrackingTouch(this)
            }
        }
        return super.onTouchEvent(event)
    }

    private fun getSliderProgressRange() = mSliderWidth - paddingStart - paddingEnd

    private fun setThumbPosition(position: Float): SmoothSlider {
        this.mThumbPosition = position
        requestLayout()
        invalidate()
        onSliderChangeListener?.onProgressChanged(
            this, getProgress(), mTouchMode != TOUCH_MODE_IDLE
        )
        return this
    }

    fun setStep(step: Int): SmoothSlider {
        this.mStepSize = step
        return this
    }

    fun getStep(): Int {
        return this.mStepSize
    }

    fun setProgress(progress: Int): SmoothSlider {
        setThumbPosition(getThumbPositionFromProgress(progress))
        return this
    }

    fun getProgress(): Int {
        return (this.mThumbPosition * mMaxProgress).toInt()
    }

    fun getThumbPositionFromProgress(progress: Int): Float {
        return progress / mMaxProgress.toFloat()
    }

    fun setMaxProgress(maxProgress: Int): SmoothSlider {
        this.mMaxProgress = maxProgress
        invalidate()
        return this
    }

    fun getMaxProgress(): Int {
        return mMaxProgress
    }

    fun setThumbRadius(thumbRadius: Float): SmoothSlider {
        this.mThumbRadius = thumbRadius
        requestLayout()
        invalidate()
        return this
    }

    fun getThumbRadius(): Float {
        return this.mThumbRadius
    }

    fun setThumbColorResource(@ColorRes id: Int): SmoothSlider {
        return setThumbColor(context.getColorCompat(id))
    }

    fun setThumbColor(@ColorInt color: Int): SmoothSlider {
        this.mThumbColor = color
        this.mThumbPaint!!.color = color
        invalidate()
        return this
    }

    @ColorInt
    fun getThumbColor(): Int {
        return this.mThumbColor!!
    }

    fun setProgressColorResource(@ColorRes id: Int) {
        setProgressColor(context.getColorCompat(id))
    }

    fun setProgressColor(@ColorInt color: Int): SmoothSlider {
        this.mProgressColor = color
        this.mProgressPaint!!.color = color
        invalidate()
        return this
    }

    @ColorInt
    fun getProgressColor(): Int {
        return this.mProgressColor!!
    }

    fun setProgressBackgroundColorResource(@ColorRes id: Int): SmoothSlider {
        return setProgressBackgroundColor(context.getColorCompat(id))
    }

    fun setProgressBackgroundColor(@ColorInt color: Int): SmoothSlider {
        this.mProgressBackgroundColor = color
        this.mProgressBackgroundPaint!!.color = color
        invalidate()
        return this
    }

    @ColorInt
    fun getProgressBackgroundColor(): Int {
        return this.mProgressBackgroundColor!!
    }

    fun setOnSliderChangeListener(onSliderChangeListener: OnSliderChangeListener?): SmoothSlider {
        this.onSliderChangeListener = onSliderChangeListener
        return this
    }

    private var onSliderChangeListener: OnSliderChangeListener? = null


    interface OnSliderChangeListener {

        fun onProgressChanged(smoothSlider: SmoothSlider, progress: Int, fromUser: Boolean)

        fun onStartTrackingTouch(smoothSlider: SmoothSlider)

        fun onStopTrackingTouch(smoothSlider: SmoothSlider)
    }

    companion object {

        private const val TOUCH_MODE_IDLE = 0
        private const val TOUCH_MODE_DOWN = 1
        private const val TOUCH_MODE_DRAGGING = 2
    }

}