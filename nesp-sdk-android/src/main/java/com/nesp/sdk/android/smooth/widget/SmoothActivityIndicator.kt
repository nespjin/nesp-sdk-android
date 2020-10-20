package com.nesp.sdk.android.smooth.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Handler
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import com.nesp.sdk.android.R
import com.nesp.sdk.android.util.AttrUtil
import kotlin.math.*

/**
 *
 *
 * Team: NESP Technology
 * @author: <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * Time: Created 2020/10/18 19:19
 * Project: NespAndroidSdk
 **/
class SmoothActivityIndicator : View {

    private var mIndicatorColor = Color.parseColor("#A2A3B0")
    private var mStartAngle = 0
    private var mLineWidth = 0F
    private val mLineCount = 12
    private var mIsAutoStart = true
    private val minAlpha = 0
    private val mAngleGradient = 360 / mLineCount
    private var mPeriod = 60

    private val mIndicatorColors = arrayOfNulls<Int>(mLineCount)
    private var mIndicatorPaint: Paint? = null

    private val mAnimHandler = Handler()
    private val mAnimRunnable = object : Runnable {
        override fun run() {
            mStartAngle += mAngleGradient
            invalidate()
            mAnimHandler.postDelayed(this, mPeriod.toLong())
        }
    }

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) :
            this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    ) {
        val typedArray = context.obtainStyledAttributes(
            attrs, R.styleable.SmoothActivityIndicator, defStyleAttr,
            AttrUtil.getAttrOutTypeValue(context, R.attr.smoothActivityIndicatorStyle)
                .resourceId
        )

        mIndicatorColor =
            typedArray.getColor(R.styleable.SmoothActivityIndicator_indicatorColor, mIndicatorColor)

        mStartAngle = typedArray.getInt(R.styleable.SmoothActivityIndicator_startAngle, mStartAngle)
        mLineWidth =
            typedArray.getInt(R.styleable.SmoothActivityIndicator_lineWidth, mLineWidth.toInt())
                .toFloat()
        mIsAutoStart =
            typedArray.getBoolean(R.styleable.SmoothActivityIndicator_autoStart, mIsAutoStart)
        mPeriod =
            typedArray.getInt(R.styleable.SmoothActivityIndicator_period, mPeriod)

        typedArray.recycle()

        mIndicatorPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        val alpha = Color.alpha(mIndicatorColor)
        val red = Color.red(mIndicatorColor)
        val green = Color.green(mIndicatorColor)
        val blue = Color.blue(mIndicatorColor)
        val alphaGradient = abs(alpha - minAlpha) / mLineCount
        for (i in mIndicatorColors.indices) {
            mIndicatorColors[i] = Color.argb(alpha - alphaGradient * i, red, green, blue)
        }
        mIndicatorPaint?.strokeCap = Paint.Cap.ROUND
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val centerX = width / 2
        val centerY = height / 2
        val radius =
            min(width - paddingStart - paddingEnd, height - paddingTop - paddingBottom) / 2f

        if (mLineWidth == 0F) {
            mLineWidth = pointX(mAngleGradient / 2, radius / 2) / 3
        }
        mIndicatorPaint!!.strokeWidth = mLineWidth.toFloat()
        for (i in mIndicatorColors.indices) {
            mIndicatorPaint!!.color = mIndicatorColors[i]!!
            canvas.drawLine(
                centerX + pointX(-mAngleGradient * i + mStartAngle, radius / 2),
                centerY + pointY(-mAngleGradient * i + mStartAngle, radius / 2),
                centerX + pointX(-mAngleGradient * i + mStartAngle, radius - mLineWidth / 2),
                centerY + pointY(-mAngleGradient * i + mStartAngle, radius - mLineWidth / 2),
                mIndicatorPaint!!
            )
        }

    }

    private fun pointX(angle: Int, radius: Float): Float {
        return (radius * cos(angle * Math.PI / 180)).toFloat()
    }

    private fun pointY(angle: Int, radius: Float): Float {
        return (radius * sin(angle * Math.PI / 180)).toFloat()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (mIsAutoStart) {
            start()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stop()
    }

    fun start() {
        mAnimHandler.post(mAnimRunnable)
    }

    fun stop() {
        mAnimHandler.removeCallbacks(mAnimRunnable)
    }

    fun setIndicatorColor(@ColorInt color: Int): SmoothActivityIndicator {
        this.mIndicatorColor = color
        return this
    }

    fun setLineWidth(width: Float): SmoothActivityIndicator {
        this.mLineWidth = width
        return this
    }

    fun setStartAngle(startAngle: Int): SmoothActivityIndicator {
        this.mStartAngle = startAngle
        return this
    }

    fun setPeriod(period: Int): SmoothActivityIndicator {
        this.mPeriod = period
        return this
    }


}