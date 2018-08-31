package com.dolgushin.yu.meetspinprogress

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.DecelerateInterpolator

class MeatSpinProgressBar : View {
    private var orientation = 0
    private var primaryColor = 0
    private var secondaryColor = 0
    private var isLoop = true
    private var primaryPaint = Paint()
    private var secondaryPaint = Paint()
    private var ballRadiusCycle = 0f
    private var trunkRadiusCycle = 0f
    private var currentTiltAngle = 0f
    private lateinit var cycleAnimator: ValueAnimator
    private var isCycleRunning = true

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
        isCycleRunning = true
        cycleAnimator = ValueAnimator.ofFloat(0f, 360f)
        cycleAnimator.duration = 1250
        cycleAnimator.interpolator = DecelerateInterpolator()
        cycleAnimator.repeatCount = ValueAnimator.INFINITE
        cycleAnimator.addUpdateListener {
            currentTiltAngle = it.animatedValue as Float
            invalidate()
        }
        startCycleAnimation()
    }

    fun startCycleAnimation() {
        cycleAnimator.start()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawCycleLoopProgress(canvas)
    }

    private fun drawCycleLoopProgress(canvas: Canvas) {
        ballRadiusCycle = (width - dpToPx(2f)) / 8
        trunkRadiusCycle = ballRadiusCycle / 2
        canvas.drawCircle(getCenterW() - ballRadiusCycle, getCenterH().toFloat(), ballRadiusCycle, primaryPaint)
        canvas.drawCircle(getCenterW() + ballRadiusCycle, getCenterH().toFloat(), ballRadiusCycle, primaryPaint)
        canvas.save()
        canvas.rotate(currentTiltAngle, getCenterW().toFloat(), getCenterH().toFloat())
        canvas.drawRoundRect(
                getCenterW() - trunkRadiusCycle,
                dpToPx(1f),
                getCenterW() + trunkRadiusCycle,
                getCenterH().toFloat(),
                trunkRadiusCycle,
                trunkRadiusCycle,
                primaryPaint)
        canvas.restore()
    }
}