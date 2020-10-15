package com.nesp.sdk.android.smooth.widget


import android.animation.ObjectAnimator
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.text.Layout
import android.text.TextPaint
import android.text.method.TransformationMethod
import android.util.AttributeSet
import android.util.Property
import android.view.MotionEvent
import android.view.VelocityTracker
import android.widget.CompoundButton
import androidx.annotation.IntDef
import androidx.appcompat.widget.TintTypedArray
import com.nesp.sdk.android.R

/**
 *
 * Author: <a href="mailto:1756404649@qq.com">JinZhaolu Email:1756404649@qq.com</a>
 * Time: Created 2020/10/15 8:41 AM
 * Project: NespAndroidSdk
 * Description:
 **/
class SmoothSwitch : CompoundButton {

    private var mThumbDrawable: Drawable? = null
    private var mThumbTintList: ColorStateList? = null
    private var mThumbTintMode: PorterDuff.Mode? = null
    private var mHasThumbTint = false
    private var mHasThumbTintMode = false

    private var mTrackDrawable: Drawable? = null
    private var mTrackTintList: ColorStateList? = null
    private var mTrackTintMode: PorterDuff.Mode? = null
    private var mHasTrackTint = false
    private var mHasTrackTintMode = false

    private val mThumbTextPadding = 0
    private val mSwitchMinWidth = 0
    private val mSwitchPadding = 0
    private val mSplitTrack = false
    private val mTextOn: CharSequence? = null
    private val mTextOff: CharSequence? = null
    private val mShowText = false

    @TouchMode
    private val mTouchMode = 0
    private val mTouchSlop = 0
    private val mTouchX = 0f
    private val mTouchY = 0f
    private val mVelocityTracker = VelocityTracker.obtain()
    private val mMinFlingVelocity = 0

    var mThumbPosition = 0f
        set(value) {
            field = value
            invalidate()
        }

    /**
     * Width required to draw the switch track and thumb. Includes padding and
     * optical bounds for both the track and thumb.
     */
    private val mSwitchWidth = 0

    /**
     * Height required to draw the switch track and thumb. Includes padding and
     * optical bounds for both the track and thumb.
     */
    private val mSwitchHeight = 0

    /**
     * Width of the thumb's content region. Does not include padding or
     * optical bounds.
     */
    private val mThumbWidth = 0

    /** Left bound for drawing the switch track and thumb.  */
    private val mSwitchLeft = 0

    /** Top bound for drawing the switch track and thumb.  */
    private val mSwitchTop = 0

    /** Right bound for drawing the switch track and thumb.  */
    private val mSwitchRight = 0

    /** Bottom bound for drawing the switch track and thumb.  */
    private val mSwitchBottom = 0

    private var mTextPaint: TextPaint? = null
    private val mTextColors: ColorStateList? = null
    private val mOnLayout: Layout? = null
    private val mOffLayout: Layout? = null
    private val mSwitchTransformationMethod: TransformationMethod? = null
    var mPositionAnimator: ObjectAnimator? = null
    private val mTextHelper: AppCompatTextHelper? = null

    private val mTempRect = Rect()

    private val CHECKED_STATE_SET = intArrayOf(
        android.R.attr.state_checked
    )

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) :
            this(context, attrs, R.attr.smoothSwitchStyle)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    ) {
        mTextPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)

        val resources = resources
        mTextPaint!!.density = resources.displayMetrics.density


    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event)
    }

    companion object {
        private const val THUMB_ANIMATION_DURATION = 250

        @IntDef(TouchMode.IDLE, TouchMode.DOWN, TouchMode.DRAGGING)
        private annotation class TouchMode {
            companion object {
                internal const val IDLE = 0
                internal const val DOWN = 1
                internal const val DRAGGING = 2
            }
        }

        // We force the accessibility events to have a class name of SmoothSwitch, since screen
        // readers already know how to handle their events
        private const val ACCESSIBILITY_EVENT_CLASS_NAME =
            "com.nesp.sdk.android.smooth.widget.SmoothSwitch"

        // Enum for the "typeface" XML parameter.
        private const val SANS = 1
        private const val SERIF = 2
        private const val MONOSPACE = 3

        private val THUMB_POS =
            object : Property<SmoothSwitch, Float>(Float::class.java, "thumbPos") {
                override fun get(`object`: SmoothSwitch): Float {
                    return `object`.mThumbPosition
                }

                override fun set(`object`: SmoothSwitch, value: Float) {
                    `object`.mThumbPosition = value
                }
            }
    }
}