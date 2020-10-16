package com.nesp.sdk.android.smooth.widget

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Property
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View
import android.view.ViewConfiguration
import androidx.annotation.ColorInt
import androidx.core.view.ViewCompat
import com.nesp.sdk.android.R
import com.nesp.sdk.android.core.ktx.content.getColorCompat
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

/**
 *
 * Author: <a href="mailto:jinzhaolu@numob.com">Jack Email:jinzhaolu@numob.com</a>
 * Time: Created 2020/10/16 11:02 AM
 * Project: NespAndroidSdkSample
 * Description:
 **/
class SmoothSwitch : View {

    private var mSwitchHeight = 0F
    private var mSwitchWidth = 0F

    private var mTrackPaint: Paint? = null
    private var mTrackBackgroundPaint: Paint? = null

    private var mTrackCheckedColor: Int? = null
    private var mTrackNormalColor: Int? = null

    private var mThumbPaint: Paint? = null
    private var mThumbPadding = 10F
    internal var mThumbPosition = 0F

    private var mPositionAnimator: ObjectAnimator? = null

    private var mTextPaint: TextPaint? = null
    private var mTextOn = "ON"
    private var mTextOff = "OFF"

    private var mTouchMode: Int = 0
    private var mTouchSlop: Int = 0
    private var mTouchX: Float = 0F
    private var mTouchY: Float = 0F
    private val mVelocityTracker = VelocityTracker.obtain()
    private var mMinFlingVelocity = 0

    private var mIsChecked = false
    private var mOnCheckChangedListener: OnCheckChangedListener? = null

    constructor(context: Context, attrs: AttributeSet?) :
            this(context, attrs, R.attr.smoothSwitchStyle)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    ) {

        mTrackPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mTrackCheckedColor = context.getColorCompat(R.color.smoothSystemGreen)
        mTrackNormalColor = context.getColorCompat(R.color.smoothSystemGray)
        mTrackPaint!!.color = getTrackColor()
        mTrackPaint!!.alpha = getTrackAlpha()

        mTrackBackgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mTrackBackgroundPaint!!.color = context.getColorCompat(R.color.white)

        mThumbPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mThumbPaint!!.color = context.getColorCompat(R.color.white)
        mThumbPaint!!.alpha = 250

        val configuration = ViewConfiguration.get(context)
        mTouchSlop = configuration.scaledTouchSlop
        mMinFlingVelocity = configuration.scaledMinimumFlingVelocity

        // Refresh display with current params
        setChecked(isChecked())
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (isEnabled && hitThumb(event.x, event.y)) {
                    mTouchMode = TOUCH_MODE_DOWN
                    mTouchX = event.x
                    mTouchY = event.y
                }
            }

            MotionEvent.ACTION_MOVE -> {
                when (mTouchMode) {
                    TOUCH_MODE_IDLE -> {
                        // Didn't target the thumb, treat normally.
                    }

                    TOUCH_MODE_DOWN -> {
                        if (abs(x - mTouchX!!) > mTou)
                    }
                }
            }

            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {

            }

        }
        return super.onTouchEvent(event)
    }

    override fun performClick(): Boolean {
        toggle()
        return super.performClick()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mSwitchWidth = measuredWidth.toFloat()
        mSwitchHeight = min(mSwitchWidth / 2F, measuredHeight.toFloat())
//        mHeight = 160F
//        mWidth = 300F

    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }

    override fun onDraw(canvas: Canvas) {
        drawTrackBackground(canvas)
        drawTrackForeground(canvas)
        drawThumb(canvas)
        super.onDraw(canvas)
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
        val radius = getThumbRadius()
        canvas.drawCircle(
            getThumbCircleX(), mThumbPadding + radius, radius,
            mThumbPaint!!
        )
    }

    private fun getThumbCircleX(): Float {
        val thumbMaxCircleX = getMaxThumbCircleX()
        var thumbCircleX = getMinThumbCircleX()
        thumbCircleX = min(thumbMaxCircleX, thumbCircleX + getThumbOffset())
        return thumbCircleX
    }

    private fun getThumbOffset(): Float {
        val maxOffset = mSwitchWidth - 2 * getThumbRadius()
        return maxOffset * mThumbPosition
    }

    private fun getMaxThumbCircleX(): Float {
        return mSwitchWidth - mThumbPadding - getThumbRadius()
    }

    private fun getMinThumbCircleX(): Float {
        return mThumbPadding + getThumbRadius()
    }

    private fun getThumbRadius(): Float {
        return (mSwitchHeight - 2 * mThumbPadding) / 2
    }

    @ColorInt
    private fun getTrackColor(): Int {
        val fromColor = if (isChecked()) mTrackNormalColor else mTrackCheckedColor
        val toColor = if (isChecked()) mTrackCheckedColor else mTrackNormalColor
        if (mThumbPosition in 0F..0.5F) {

        } else {

        }
        return if (mThumbPosition in 0F..0.5F) {
            context.getColorCompat(R.color.smoothSystemGray)
        } else {
            context.getColorCompat(R.color.smoothSystemGreen)
        }
    }

    private fun getTrackAlpha(): Int {
        return if (mThumbPosition in 0F..0.5F) {
            (255 * (0.5F - mThumbPosition) / 0.5).toInt()
        } else {
            (255 * (mThumbPosition - 0.5) / 0.5).toInt()
        }
    }

    private fun hitThumb(x: Float, y: Float): Boolean {
        val thumbCircleX = getThumbCircleX()
        val thumbRadius = getThumbRadius()
        var maxThumbX = min(thumbCircleX + thumbRadius, mSwitchWidth - mThumbPadding)
        var minThumbX = max(thumbCircleX - thumbRadius, mThumbPadding)
        minThumbX = min(minThumbX, maxThumbX)
        maxThumbX = max(minThumbX, maxThumbX)
        return (y in mThumbPadding..mSwitchHeight - mThumbPadding) &&
                (x in minThumbX..maxThumbX)
    }

    override fun setOnClickListener(onClickListener: OnClickListener?) {

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
    }

}