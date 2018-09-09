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
import com.dolgushin.yu.meetspinprogress.enums.HorizontalMeatDistance
import com.dolgushin.yu.meetspinprogress.enums.MeatOrientation
import com.dolgushin.yu.meetspinprogress.enums.HorizontalMeatQuantity

class MeatSpinProgressBar : View {

    //Horizontal loop property
    private var quantityHorizontalMeats = 0
    private var horizontalDistance = 0f

    //Property
    private var orientation = 0
    private var primaryColor = 0
    private var secondaryColor = 0
    private var isLoop = true
    private var isGayColors = false

    //Paints
    private var primaryPaint = Paint()
    private var secondaryPaint = Paint()

    //Cycle loop progress property
    private var ballRadius = 0f
    private var trunkRadius = 0f
    private var crownRadius = 0f

    //Sup object for loop horizontal progress
    private lateinit var flyingHorizontalMeats: FlyingHorizontalMeats

    //Animators for loop progress
    private lateinit var cycleAnimator: ValueAnimator
    private lateinit var horizontalAnimator: ValueAnimator

    //Animators counters
    private var horizontalAnimatorCounter = 0f
    private var currentTiltAngle = 0f

    constructor(context: Context) : super(context) {
        orientation = MeatOrientation.CYCLE.orientation
        primaryColor = Color.RED
        secondaryColor = Color.GRAY
        isLoop = true
        quantityHorizontalMeats = HorizontalMeatQuantity.FOUR.quantity
        horizontalDistance = HorizontalMeatDistance.SMALL.distance
        isGayColors = false
        initializeMeatSpinProgressBar()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.MeatSpinProgressBar)
        orientation = typedArray.getInt(R.styleable.MeatSpinProgressBar_meatOrientation, MeatOrientation.CYCLE.orientation)
        primaryColor = typedArray.getColor(R.styleable.MeatSpinProgressBar_meatPrimaryColor, Color.RED)
        secondaryColor = typedArray.getColor(R.styleable.MeatSpinProgressBar_meatSecondatyColor, Color.GRAY)
        isLoop = typedArray.getBoolean(R.styleable.MeatSpinProgressBar_meatIsLoop, true)
        quantityHorizontalMeats = typedArray.getInt(R.styleable.MeatSpinProgressBar_meatHorizontalQuantity, HorizontalMeatQuantity.FOUR.quantity)
        horizontalDistance = typedArray.getFloat(R.styleable.MeatSpinProgressBar_meatHorizontalDistance, HorizontalMeatDistance.SMALL.distance)
        isGayColors = typedArray.getBoolean(R.styleable.MeatSpinProgressBar_meatGayColors, false)
        typedArray.recycle()
        initializeMeatSpinProgressBar()
    }

    //Main init method
    private fun initializeMeatSpinProgressBar() {
        primaryPaint.isAntiAlias = true
        primaryPaint.color = primaryColor
        primaryPaint.style = Paint.Style.FILL

        secondaryPaint.isAntiAlias = true
        secondaryPaint.color = secondaryColor
        secondaryPaint.style = Paint.Style.FILL

        initCycleAnimator()
        initHorizontalAnimator()
        flyingHorizontalMeats = FlyingHorizontalMeats()
        flyingHorizontalMeats.quantity = quantityHorizontalMeats
        flyingHorizontalMeats.betweenDistance = horizontalDistance
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        startAnimationProgress()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stopAnimationProgress()
    }

    // Start anim if property isLoop equals true
    private fun startAnimationProgress() {
        if (isLoop) {
            if (orientation == MeatOrientation.CYCLE.orientation) cycleAnimator.start()
            else horizontalAnimator.start()
        }
    }


    //Stop anim if it running
    private fun stopAnimationProgress() {
        if (cycleAnimator.isRunning) cycleAnimator.cancel()
        if (horizontalAnimator.isRunning) horizontalAnimator.cancel()
    }

    fun initHorizontalAnimator() {
        horizontalAnimator = ValueAnimator.ofFloat(0f, 100f)
        horizontalAnimator.duration = 1250
        horizontalAnimator.interpolator = LinearInterpolator()
        horizontalAnimator.repeatCount = ValueAnimator.INFINITE
        horizontalAnimator.addUpdateListener {
            horizontalAnimatorCounter = it.animatedValue as Float
            invalidate()
        }
    }

    fun initCycleAnimator() {
        cycleAnimator = ValueAnimator.ofFloat(0f, 360f)
        cycleAnimator.duration = 1250
        cycleAnimator.interpolator = DecelerateInterpolator()
        cycleAnimator.repeatCount = ValueAnimator.INFINITE
        cycleAnimator.addUpdateListener {
            currentTiltAngle = it.animatedValue as Float
            invalidate()
        }
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (orientation == MeatOrientation.CYCLE.orientation) drawCycleLoopProgress(canvas)
        else drawHorizontalLoopProgress(canvas)
    }


    private fun drawHorizontalLoopProgress(canvas: Canvas) {

        ballRadius = Math.min(height, width) / 8f
        trunkRadius = ballRadius / 1.5f
        crownRadius = trunkRadius + 2f
        val step = width / 100f
        flyingHorizontalMeats.calculateMeats(width, height)
        flyingHorizontalMeats.updateMeatsPosition(step * horizontalAnimatorCounter)
        for (meat in flyingHorizontalMeats.meats) {
            if (isGayColors) primaryPaint.color = meat.color
            canvas.drawRoundRect(meat.trunk, flyingHorizontalMeats.oneMeatSizeHeight, flyingHorizontalMeats.oneMeatSizeHeight, primaryPaint)
            canvas.drawCircle(meat.ballOneCenter.x, meat.ballOneCenter.y, meat.ballRadius, primaryPaint)
            canvas.drawCircle(meat.ballTwoCenter.x, meat.ballTwoCenter.y, meat.ballRadius, primaryPaint)
            canvas.drawRoundRect(meat.secondaryTrunk, flyingHorizontalMeats.oneMeatSizeHeight, flyingHorizontalMeats.oneMeatSizeHeight, primaryPaint)
            canvas.drawCircle(meat.ballOneSecondaryCenter.x, meat.ballOneSecondaryCenter.y, meat.ballRadius, primaryPaint)
            canvas.drawCircle(meat.ballTwoSecondaryCenter.x, meat.ballTwoSecondaryCenter.y, meat.ballRadius, primaryPaint)
        }
        primaryPaint.color = primaryColor
    }

    private fun drawCycleLoopProgress(canvas: Canvas) {
        ballRadius = (Math.min(width, height) - dpToPx(2f)) / 8
        trunkRadius = ballRadius / 1.5f
        crownRadius = trunkRadius + 2f
        canvas.drawCircle(getCenterW() - ballRadius, getCenterH().toFloat(), ballRadius, primaryPaint)
        canvas.drawCircle(getCenterW() + ballRadius, getCenterH().toFloat(), ballRadius, primaryPaint)
        canvas.save()
        canvas.rotate(currentTiltAngle, getCenterW().toFloat(), getCenterH().toFloat())
        canvas.drawRoundRect(
                getCenterW() - trunkRadius,
                dpToPx(1f) + crownRadius,
                getCenterW() + trunkRadius,
                getCenterH().toFloat(),
                trunkRadius,
                trunkRadius,
                primaryPaint)
        canvas.drawCircle(getCenterW().toFloat(), dpToPx(1f) + crownRadius, crownRadius, primaryPaint)
        canvas.restore()
    }

}