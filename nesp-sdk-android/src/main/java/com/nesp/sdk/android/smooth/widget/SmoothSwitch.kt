package com.nesp.sdk.android.smooth.widget

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Property
import android.view.View
import androidx.core.view.ViewCompat
import com.nesp.sdk.android.R
import com.nesp.sdk.android.core.ktx.content.getColorCompat

/**
 *
 * Author: <a href="mailto:jinzhaolu@numob.com">Jack Email:jinzhaolu@numob.com</a>
 * Time: Created 2020/10/16 11:02 AM
 * Project: NespAndroidSdkSample
 * Description:
 **/
class SmoothSwitch : View {

    private var mTrackPaint: Paint? = null
    private var mThumbPaint: Paint? = null
    private var mTextPaint: TextPaint? = null

    private var mHeight = 0F
    private var mWidth = 0F

    private var mThumbPadding = 10F
    internal var mThumbPosition = 0F

    internal var mPositionAnimator: ObjectAnimator? = null

    private var isChecked = false

    constructor(context: Context, attrs: AttributeSet?) :
            this(context, attrs, R.attr.smoothSwitchStyle)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    ) {

        mTrackPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mTrackPaint!!.strokeWidth = 5F
        mTrackPaint!!.color = context.getColorCompat(R.color.smoothSystemGreen)

        mThumbPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mThumbPaint!!.color = context.getColorCompat(R.color.white)

        setOnClickListener {
            animateThumbToCheckedState(!isChecked)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }

    override fun onDraw(canvas: Canvas) {
        mHeight = 160F
        mWidth = 300F
        drawTrack(canvas)


        val radius = (mHeight - 2 * mThumbPadding) / 2
        canvas.drawCircle(
            mThumbPadding + radius, mThumbPadding + radius, radius,
            mThumbPaint!!
        )
        super.onDraw(canvas)
    }

    private fun drawTrack(canvas: Canvas) {
        val radiusArc = mHeight / 2
        canvas.drawRect(radiusArc, 0F, mWidth - radiusArc, mHeight, mTrackPaint!!)
        canvas.drawArc(0F, 0F, radiusArc * 2, mHeight, 90F, 180F, true, mTrackPaint!!)
        canvas.drawArc(mWidth - radiusArc * 2, 0F, mWidth, mHeight, -90F, 180F, true, mTrackPaint!!)
    }

    fun toggle() {
        setChecked(!isChecked())
    }

    fun setChecked(checked: Boolean): SmoothSwitch {
        if (windowToken != null && ViewCompat.isLaidOut(this)) {
            animateThumbToCheckedState(checked)
        } else {
            // Immediately move the thumb to the new position.
            cancelPositionAnimator()
            setThumbPosition(if (checked) 1F else 0F)
        }
        this.isChecked = checked
        return this
    }

    fun isChecked(): Boolean {
        return this.isChecked
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
        isChecked = !newCheckedState
    }

    private fun cancelPositionAnimator() {
        mPositionAnimator?.cancel()
    }

    private fun getTargetCheckedState(): Boolean {
        return mThumbPosition > 0.5F
    }

    companion object {

        private const val THUMB_ANIMATION_DURATION = 250L

        private val THUMB_POS: Property<SmoothSwitch, Float> =
            object : Property<SmoothSwitch, Float>(
                Float::class.java, "thumbPos"
            ) {
                override fun get(`object`: SmoothSwitch): Float {
                    return `object`.mThumbPosition
                }

                override fun set(`object`: SmoothSwitch, value: Float) {
                    `object`.mThumbPosition = value
                }
            }
    }

}