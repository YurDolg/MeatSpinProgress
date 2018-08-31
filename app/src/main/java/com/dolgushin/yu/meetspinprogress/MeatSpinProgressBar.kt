package com.dolgushin.yu.meetspinprogress

import android.content.Context
import android.util.AttributeSet
import android.view.View

class MeatSpinProgressBar : View {
    var orientation = MeatOrientation.CYCLE.orientation

    constructor(context: Context) : super(context) {
        initializeView()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initializeView()
    }

    private fun initializeView() {}
}