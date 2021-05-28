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

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Property
import android.view.*
import androidx.annotation.ColorInt
import androidx.core.view.ViewCompat
import com.nesp.sdk.android.R
import com.nesp.sdk.android.core.ktx.content.getColorCompat
import com.nesp.sdk.android.core.ktx.graphics.getFontHeight
import com.nesp.sdk.android.core.ktx.graphics.getFontWidth
import com.nesp.sdk.android.util.AttrUtil
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

/**
 *
 * Author: <a href="mailto:1756404649@qq.com">JinZhaolu Email:1756404649@qq.com</a>
 * Time: Created 2020/10/16 11:02 AM
 * Project: NespAndroidSdk
 * Description:
 **/
class SmoothSwitch : View {

    private var mSwitchHeight = 0F
    private var mSwitchWidth = 0F

    private var mTrackPaint: Paint? = null
    private var mTrackBackgroundPaint: Paint? = null

    private var mTrackColorActive: Int = -1
    private var mTrackColorInactive: Int = -1

    private var mThumbPaint: Paint? = null
    private var mThumbPadding = 10F
    internal var mThumbPosition = 0.5F

    @ColorInt
    private var mThumbColor: Int = Color.WHITE
    private var mThumbRadius: Float = -1F
    private var mThumbElevation: Float = 5F

    private var mPositionAnimator: ObjectAnimator? = null

    private var mShowText = false
    private var mTextPaint: TextPaint? = null
    private var mTextOn = "开"
    private var mTextOff = "关"
    private var mTextPadding = 20F
    private var mTextSize = 70F

    @ColorInt
    private var mTextColorActive: Int? = null

    @ColorInt
    private var mTextColorInactive: Int? = null

    private var mTouchMode: Int = 0
    private var mTouchSlop: Int = 0
    private var mTouchX: Float = 0F
    private var mTouchY: Float = 0F
    private val mVelocityTracker = VelocityTracker.obtain()
    private var mMinFlingVelocity = 0

    private var mIsChecked = false
    private var mOnCheckChangedListener: OnCheckChangedListener? = null

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) :
            this(context, attrs, 0)

    @SuppressLint("RestrictedApi")
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    ) {

        val typedArray =
            context.obtainStyledAttributes(
                attrs, R.styleable.SmoothSwitch, defStyleAttr,
                AttrUtil.getAttrOutTypeValue(context, R.attr.smoothSwitchStyle).resourceId
            )
        mIsChecked = typedArray.getBoolean(R.styleable.SmoothSwitch_android_checked, mIsChecked)
        typedArray.getString(R.styleable.SmoothSwitch_android_textOn)?.apply { mTextOn = this }
        typedArray.getString(R.styleable.SmoothSwitch_android_textOff)?.apply { mTextOff = this }
        mShowText = typedArray.getBoolean(R.styleable.SmoothSwitch_android_showText, mShowText)
        mTextSize = typedArray.getDimension(R.styleable.SmoothSwitch_android_textSize, mTextSize)
        mTextPadding =
            typedArray.getDimension(R.styleable.SmoothSwitch_android_thumbTextPadding, mTextPadding)
        val defaultTextColorActive = context.getColorCompat(R.color.white)
        mTextColorActive =
            typedArray.getColor(R.styleable.SmoothSwitch_textColorActive, defaultTextColorActive)
        val defaultTextColorInactive = context.getColorCompat(R.color.smoothSystemGray50)
        mTextColorInactive =
            typedArray.getColor(
                R.styleable.SmoothSwitch_textColorInactive, defaultTextColorInactive
            )


        val defaultTrackColorActive = context.getColorCompat(R.color.smoothSystemGreen)
        mTrackColorActive =
            typedArray.getColor(R.styleable.SmoothSwitch_trackColorActive, defaultTrackColorActive)
        val defaultTrackColorInactive = context.getColorCompat(R.color.smoothSystemGray)
        mTrackColorInactive =
            typedArray.getColor(
                R.styleable.SmoothSwitch_trackColorInactive, defaultTrackColorInactive
            )

        mThumbColor =
            typedArray.getColor(R.styleable.SmoothSwitch_thumbColor, mThumbColor)
        mThumbRadius =
            typedArray.getDimension(R.styleable.SmoothSwitch_thumbRadius, mThumbRadius)
        mThumbElevation =
            typedArray.getDimension(R.styleable.SmoothSwitch_thumbElevation, mThumbElevation)
        mThumbPosition =
            typedArray.getDimension(R.styleable.SmoothSwitch_thumbPosition, mThumbPosition)

        if (mThumbPosition < 0F) mThumbPosition = 0F
        if (mThumbPosition > 1F) mThumbPosition = 1F


        mTrackPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mTrackPaint!!.color = getTrackColor()
        mTrackPaint!!.alpha = getTrackAlpha()

        mTrackBackgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mTrackBackgroundPaint!!.color = context.getColorCompat(R.color.white)

        mThumbPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mThumbPaint!!.color = mThumbColor
        mThumbPaint!!.alpha = 250

        mTextPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)
        mTextPaint!!.density = resources.displayMetrics.density
        mTextPaint!!.textSize = mTextSize

        val configuration = ViewConfiguration.get(context)
        mTouchSlop = configuration.scaledTouchSlop
        mMinFlingVelocity = configuration.scaledMinimumFlingVelocity

        typedArray.recycle()

        // Refresh display with current params
        setChecked(isChecked())
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {
        mVelocityTracker.addMovement(event)
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (isEnabled && hitThumb(event.x, event.y)) {
                    mTouchMode = TOUCH_MODE_DOWN
                    mTouchX = event.x
                    mTouchY = event.y
                } else {
                    performClick()
                }
                return true
            }

            MotionEvent.ACTION_MOVE -> {
                when (mTouchMode) {
                    TOUCH_MODE_IDLE -> {
                        // Didn't target the thumb, treat normally.
                    }

                    TOUCH_MODE_DOWN -> {
                        if (abs(x - mTouchX) > mTouchSlop ||
                            abs(y - mTouchY) > mTouchSlop
                        ) {
                            mTouchMode = TOUCH_MODE_DRAGGING
                            parent.requestDisallowInterceptTouchEvent(true)
                            mTouchX = event.x
                            mTouchY = event.y
                            return true
                        }
                    }

                    TOUCH_MODE_DRAGGING -> {
                        val thumbScrollRange: Int = getThumbScrollRange()
                        val thumbScrollOffset = event.x - mTouchX
                        var dPos: Float
                        dPos = if (thumbScrollRange != 0) {
                            thumbScrollOffset / thumbScrollRange
                        } else {
                            // If the thumb scroll range is empty, just use the
                            // movement direction to snap on or off.
                            if (thumbScrollOffset > 0) 1F else -1F
                        }
                        if (isLayoutRtl()) {
                            dPos = -dPos
                        }
                        val newPos = constrain(mThumbPosition + dPos, 0f, 1f)
                        if (newPos != mThumbPosition) {
                            mTouchX = event.x
                            setThumbPosition(newPos)
                        }
                        return true
                    }
                }
            }

            MotionEvent.ACTION_UP,
            MotionEvent.ACTION_CANCEL -> {
                if (mTouchMode == TOUCH_MODE_DRAGGING) {
                    stopDrag(event)
                    // Allow super class to handle pressed state, etc.
                    super.onTouchEvent(event)
                    return true
                } else {
                    if (mTouchX == event.x && mTouchY == event.y) {
                        performClick()
                        return true
                    }
                }
                mTouchMode = TOUCH_MODE_IDLE
                mVelocityTracker.clear()
            }

        }
        return super.onTouchEvent(event)
    }

    private fun stopDrag(event: MotionEvent) {
        mTouchMode = TOUCH_MODE_IDLE

        // Commit the change if the event is up and not canceled and the switch
        // has not been disabled during the drag.
        val commitChange = event.action == MotionEvent.ACTION_UP && isEnabled
        val oldState = isChecked()
        val newState: Boolean = if (commitChange) {
            mVelocityTracker.computeCurrentVelocity(1000)
            val xVelocity = mVelocityTracker.xVelocity
            if (abs(xVelocity) > mMinFlingVelocity) {
                if (isLayoutRtl()) xVelocity < 0 else xVelocity > 0
            } else {
                getTargetCheckedState()
            }
        } else {
            oldState
        }

        if (newState != oldState) {
            playSoundEffect(SoundEffectConstants.CLICK)
        }
        // Always call setChecked so that the thumb is moved back to the correct edge
        setChecked(newState)
        cancelSuperTouch(event)
    }

    private fun cancelSuperTouch(ev: MotionEvent) {
        val cancel = MotionEvent.obtain(ev)
        cancel.action = MotionEvent.ACTION_CANCEL
        super.onTouchEvent(cancel)
        cancel.recycle()
    }

    private fun isLayoutRtl(): Boolean {
        return ViewCompat.getLayoutDirection(this) == ViewCompat.LAYOUT_DIRECTION_RTL
    }

    override fun performClick(): Boolean {
        toggle()
        val handled = super.performClick()
        if (!handled) {
            // View only makes a sound effect if the onClickListener was
            // called, so we'll need to make one here instead.
            playSoundEffect(SoundEffectConstants.CLICK)
        }
        return handled
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mSwitchWidth = measuredWidth.toFloat()
        mSwitchHeight = min(mSwitchWidth / 2F, measuredHeight.toFloat())
    }

    override fun onDraw(canvas: Canvas) {
//        drawTrackBackground(canvas)
        drawTrackForeground(canvas)
        drawThumb(canvas)
        drawText(canvas)
        super.onDraw(canvas)
    }

    private fun drawText(canvas: Canvas) {
        if (!mShowText) return
        if (mThumbPosition in 0F..0.3F) {
            mTextPaint!!.color = mTextColorInactive!!
            mTextPaint!!.alpha = ((0.3F - mThumbPosition) / 0.3F).toInt() * 255
            val textWidth = mTextPaint!!.getFontWidth(mTextOff)
            val textHeight = mTextPaint!!.getFontHeight()
            canvas.drawText(
                mTextOff,
                getMaxThumbCircleX() - textWidth / 2,
                (mSwitchHeight + textHeight) / 2,
                mTextPaint!!
            )
        } else if (mThumbPosition in 0.7F..1F) {
            mTextPaint!!.color = mTextColorActive!!
            mTextPaint!!.alpha = ((mThumbPosition - 0.7F) / 0.3F).toInt() * 255
            val textWidth = mTextPaint!!.getFontWidth(mTextOn)
            val textHeight = mTextPaint!!.getFontHeight()
            canvas.drawText(
                mTextOn,
                max(getMinThumbCircleX() - textWidth / 2, mTextPadding),
                (mSwitchHeight + textHeight) / 2,
                mTextPaint!!
            )
        }
    }

    private fun drawTrackForeground(canvas: Canvas) {
        mTrackPaint!!.color = getTrackColor()
        mTrackPaint!!.alpha = getTrackAlpha()
        drawTrack(canvas, mTrackPaint!!)
    }

    private fun drawTrackBackground(canvas: Canvas) {
        drawTrack(canvas, mTrackBackgroundPaint!!)
    }

    private fun drawTrack(canvas: Canvas, paint: Paint) {
        val radiusArc = mSwitchHeight / 2
        canvas.drawRect(
            radiusArc, 0F, mSwitchWidth - radiusArc, mSwitchHeight,
            paint
        )
        canvas.drawArc(
            0F, 0F, radiusArc * 2, mSwitchHeight, 90F,
            180F, true, paint
        )
        canvas.drawArc(
            mSwitchWidth - radiusArc * 2, 0F, mSwitchWidth, mSwitchHeight, -90F,
            180F, true, paint
        )
    }

    private fun drawThumb(canvas: Canvas) {
        val elevationX = if (mThumbPosition != 1F) 5F else 0F
        val elevationY = if (mThumbPosition != 1F) 5F else 0F

        mThumbPaint!!.setShadowLayer(
            mThumbElevation, elevationX, elevationY,
            context.getColorCompat(
                AttrUtil.getAttrOutTypeValue(context, R.attr.smoothElevationColor).resourceId
            )
        )
        val radius = getThumbRadius()
        canvas.drawCircle(
            getThumbCircleX(), mThumbPadding + paddingTop + radius, radius,
            mThumbPaint!!
        )
    }

    private fun getThumbCircleX(): Float {
        val thumbMaxCircleX = getMaxThumbCircleX()
        var thumbCircleX = getMinThumbCircleX()
        thumbCircleX = min(thumbMaxCircleX, thumbCircleX + getThumbOffset())
        return thumbCircleX
    }

    private fun getThumbScrollRange(): Int {
        return (mSwitchWidth - getThumbRadius() * 2 - mThumbPadding * 2 - paddingLeft - paddingRight)
            .toInt()
    }

    private fun getThumbOffset(): Float {
        val maxOffset = mSwitchWidth - 2 * getThumbRadius()
        return maxOffset * mThumbPosition
    }

    private fun getMaxThumbCircleX(): Float {
        return mSwitchWidth - mThumbPadding - paddingRight - getThumbRadius()
    }

    private fun getMinThumbCircleX(): Float {
        return paddingLeft + mThumbPadding + getThumbRadius()
    }

    private fun getThumbRadius(): Float {
        if (mThumbRadius > 0F) return mThumbRadius
        return (mSwitchHeight - 2 * mThumbPadding - paddingTop - paddingBottom) / 2
    }

    @ColorInt
    private fun getTrackColor(): Int {
        return if (mThumbPosition in 0F..0.5F) {
            // Switch is in inactive state
            mTrackColorInactive
        } else {
            // Switch is in active state
            mTrackColorActive
        }
    }

    private fun getTrackAlpha(): Int {
        val minAlpha = 80
        return if (mThumbPosition in 0F..0.5F) {
            max((255 * (0.5F - mThumbPosition) / 0.5).toInt(), minAlpha)
        } else {
            max((255 * (mThumbPosition - 0.5) / 0.5).toInt(), minAlpha)
        }
    }

    private fun hitThumb(x: Float, y: Float): Boolean {
        val thumbCircleX = getThumbCircleX()
        val thumbRadius = getThumbRadius()
        var maxThumbX = min(
            thumbCircleX + thumbRadius, mSwitchWidth -
                    mThumbPadding - paddingRight
        )
        var minThumbX = max(thumbCircleX - thumbRadius, mThumbPadding + paddingLeft)
        minThumbX = min(minThumbX, maxThumbX)
        maxThumbX = max(minThumbX, maxThumbX)
        return (y in (mThumbPadding + paddingTop)..(mSwitchHeight - mThumbPadding - paddingBottom)) &&
                (x in minThumbX..maxThumbX)
    }

    override fun setOnClickListener(onClickListener: OnClickListener?) {
        // Disable setOnClickListener
    }

    fun toggle() {
        setChecked(!isChecked())
    }

    fun setChecked(checked: Boolean): SmoothSwitch {
        if (!isEnabled) return this

        if (windowToken != null && ViewCompat.isLaidOut(this)) {
            animateThumbToCheckedState(checked)
        } else {
            // Immediately move the thumb to the new position.
            cancelPositionAnimator()
            setThumbPosition(if (checked) 1F else 0F)
        }
        this.mIsChecked = checked
        mOnCheckChangedListener?.onChanged(this, isChecked())
        return this
    }

    fun isChecked(): Boolean {
        return this.mIsChecked
    }

    fun setOnCheckChangedListener(onCheckChangedListener: OnCheckChangedListener): SmoothSwitch {
        this.mOnCheckChangedListener = onCheckChangedListener
        return this
    }

    fun setSwitchTypeface(typeface: Typeface) {
        if ((mTextPaint!!.typeface != null && mTextPaint!!.typeface != typeface) ||
            (mTextPaint!!.typeface == null)
        ) {
            mTextPaint!!.typeface = typeface
            invalidate()
        }
    }

    fun setShowText(showText: Boolean): SmoothSwitch {
        this.mShowText = showText
        return this
    }

    internal fun setThumbPosition(position: Float): SmoothSwitch {
        this.mThumbPosition = position
        invalidate()
        return this
    }

    private fun animateThumbToCheckedState(newCheckedState: Boolean) {
        val targetPosition = if (newCheckedState) 1F else 0F
        mPositionAnimator = ObjectAnimator.ofFloat(this, THUMB_POS, targetPosition)
        mPositionAnimator!!.duration = THUMB_ANIMATION_DURATION
        mPositionAnimator!!.setAutoCancel(true)
        mPositionAnimator!!.start()
        mIsChecked = !newCheckedState
    }

    private fun cancelPositionAnimator() {
        mPositionAnimator?.cancel()
    }

    private fun getTargetCheckedState(): Boolean {
        return mThumbPosition > 0.5F
    }

    interface OnCheckChangedListener {
        fun onChanged(smoothSwitch: SmoothSwitch, isChecked: Boolean)
    }

    companion object {

        private const val THUMB_ANIMATION_DURATION = 250L
        private const val TOUCH_MODE_IDLE = 0
        private const val TOUCH_MODE_DOWN = 1
        private const val TOUCH_MODE_DRAGGING = 2

        private val THUMB_POS: Property<SmoothSwitch, Float> =
            object : Property<SmoothSwitch, Float>(
                Float::class.java, "thumbPos"
            ) {
                override fun get(`object`: SmoothSwitch): Float {
                    return `object`.mThumbPosition
                }

                override fun set(`object`: SmoothSwitch, value: Float) {
                    `object`.setThumbPosition(value)
                }
            }


        /**
         * Taken from android.util.MathUtils
         */
        private fun constrain(amount: Float, low: Float, high: Float): Float {
            return if (amount < low) low else if (amount > high) high else amount
        }
    }

}