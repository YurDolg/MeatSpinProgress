package com.dolgushin.yu.meetspinprogress

import android.graphics.Color

class ColorsManager {
    private var current = 0
    private val colors = arrayListOf(Color.RED, Color.rgb(255, 128, 0), Color.YELLOW, Color.GREEN, Color.rgb(0, 191, 255),
            Color.rgb(0, 77, 255), Color.rgb(90, 0, 157))

    fun getColor(): Int {
        val returnColor = colors[current]
        if (current < colors.size - 1) current = current + 1
        else current = 0
        return returnColor
    }

    fun reset() {
        current = 0
    }
}