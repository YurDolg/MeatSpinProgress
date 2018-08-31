package com.dolgushin.yu.meetspinprogress

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class MeatSpinProgressBar : View {
    private var orientation = 0
    private var primaryColor = 0
    private var secondaryColor = 0
    private var isLoop = true
    private var primaryPaint = Paint()
    private var secondaryPaint = Paint()


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
    }


}