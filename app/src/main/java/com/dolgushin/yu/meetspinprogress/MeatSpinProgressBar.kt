package com.dolgushin.yu.meetspinprogress

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator

class MeatSpinProgressBar : View {
    private var orientation = 0
    private var primaryColor = 0
    private var secondaryColor = 0
    private var isLoop = true
    private var primaryPaint = Paint()
    private var secondaryPaint = Paint()
    private var ballRadius = 0f
    private var trunkRadius = 0f
    private var currentTiltAngle = 0f
    private var currentTrunkPosition = 0f
    private lateinit var cycleAnimator: ValueAnimator
    private lateinit var horizontalAnimator: ValueAnimator

    constructor(context: Context) : super(context) {
        orientation = MeatOrientation.CYCLE.orientation
        primaryColor = Color.RED
        secondaryColor = Color.GRAY
        isLoop = true
        initializeView()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.MeatSpinProgressBar)
        orientation = typedArray.getInt(R.styleable.MeatSpinProgressBar_meatOrientation, MeatOrientation.CYCLE.orientation)
        primaryColor = typedArray.getColor(R.styleable.MeatSpinProgressBar_meatPrimaryColor, Color.RED)
        secondaryColor = typedArray.getColor(R.styleable.MeatSpinProgressBar_meatSecondatyColor, Color.GRAY)
        isLoop = typedArray.getBoolean(R.styleable.MeatSpinProgressBar_meatIsLoop, true)
        typedArray.recycle()
        initializeView()
    }

    private fun initializeView() {
        primaryPaint.isAntiAlias = true
        primaryPaint.color = primaryColor
        primaryPaint.style = Paint.Style.FILL


        secondaryPaint.isAntiAlias = true
        secondaryPaint.color = secondaryColor
        secondaryPaint.style = Paint.Style.FILL


        cycleAnimator = ValueAnimator.ofFloat(0f, 360f)
        cycleAnimator.duration = 1250
        cycleAnimator.interpolator = DecelerateInterpolator()
        cycleAnimator.repeatCount = ValueAnimator.INFINITE
        cycleAnimator.addUpdateListener {
            currentTiltAngle = it.animatedValue as Float
            invalidate()
        }

        horizontalAnimator = ValueAnimator.ofFloat(0f, 100f)
        horizontalAnimator.duration = 1250
        horizontalAnimator.interpolator = LinearInterpolator()
        horizontalAnimator.repeatCount = ValueAnimator.INFINITE
        horizontalAnimator.addUpdateListener {
            var current  = it.animatedValue as Float
            if (current < 50f) currentTrunkPosition = current*2
            else currentTrunkPosition  = (100-current)*2
            invalidate()

        }
        startCycleAnimation()
    }

    private fun startCycleAnimation() {
        cycleAnimator.start()
        horizontalAnimator.start()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (orientation == MeatOrientation.CYCLE.orientation) drawCycleLoopProgress(canvas)
        else drawHorizontalLoopProgress(canvas)
    }

    private fun drawHorizontalLoopProgress(canvas: Canvas) {
        ballRadius = (height - dpToPx(2f)) / 8
        trunkRadius = ballRadius/1.5f
        var rightPoint = 0f
        var step = (width-dpToPx(1f) - dpToPx(1f))/100
        if (currentTrunkPosition*step < dpToPx(1f) + ballRadius) rightPoint = dpToPx(1f) + ballRadius
        else rightPoint = currentTrunkPosition*step
        canvas.drawCircle(dpToPx(1f) + ballRadius, getCenterH() - ballRadius, ballRadius, primaryPaint)
        canvas.drawCircle(dpToPx(1f) + ballRadius, getCenterH() + ballRadius, ballRadius, primaryPaint)
        canvas.drawRoundRect(
                dpToPx(1f) + ballRadius,
                getCenterH() - trunkRadius,
                rightPoint,
                getCenterH() + trunkRadius,
                trunkRadius,
                trunkRadius,
                primaryPaint)
    }

    private fun drawCycleLoopProgress(canvas: Canvas) {
        ballRadius = (width - dpToPx(2f)) / 8
        trunkRadius = ballRadius/1.5f
        canvas.drawCircle(getCenterW() - ballRadius, getCenterH().toFloat(), ballRadius, primaryPaint)
        canvas.drawCircle(getCenterW() + ballRadius, getCenterH().toFloat(), ballRadius, primaryPaint)
        canvas.save()
        canvas.rotate(currentTiltAngle, getCenterW().toFloat(), getCenterH().toFloat())
        canvas.drawRoundRect(
                getCenterW() - trunkRadius,
                dpToPx(1f),
                getCenterW() + trunkRadius,
                getCenterH().toFloat(),
                trunkRadius,
                trunkRadius,
                primaryPaint)
        canvas.drawCircle(getCenterW().toFloat(), dpToPx(1f) + trunkRadius + 4f, trunkRadius + 2f, primaryPaint)
        canvas.restore()
    }
}