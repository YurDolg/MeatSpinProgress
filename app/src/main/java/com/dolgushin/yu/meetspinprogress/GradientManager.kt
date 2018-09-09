package com.dolgushin.yu.meetspinprogress

import android.graphics.Color


class GradientManager {
    private val sourceColors = arrayListOf(Color.RED, Color.rgb(255, 128, 0), Color.rgb(255, 255, 0), Color.GREEN, Color.rgb(0, 191, 255),
            Color.rgb(0, 77, 255))
    private var gradientColors = arrayListOf<Int>()
    fun calculateGradients() {
        for (i in 0 .. sourceColors.lastIndex) {
            if (i < sourceColors.lastIndex) {
                gradientColors.addAll(calculateIntermediateColors(sourceColors[i], sourceColors[i + 1]))
            } else{
                gradientColors.addAll(calculateIntermediateColors(sourceColors[i], sourceColors[0]))
            }
        }

    }

    private fun calculateIntermediateColors(startColor: Int, endColor: Int): ArrayList<Int> {
        val deltaRed = Color.red(endColor) - Color.red(startColor).toFloat()
        val deltaGreen = Color.green(endColor) - Color.green(startColor).toFloat()
        val deltaBule = Color.blue(endColor) - Color.blue(startColor).toFloat()
        val redStep = deltaRed / 5
        val greenStep = deltaGreen / 5
        val blueStep = deltaBule / 5
        val intermediateColors = ArrayList<Int>()
        intermediateColors.add(startColor)
        for (i in 1..5) {
            val red = Color.red(intermediateColors[i - 1]) + redStep.toInt()
            val green = Color.green(intermediateColors[i - 1]) + greenStep.toInt()
            val blue = Color.blue(intermediateColors[i - 1]) + blueStep.toInt()
            intermediateColors.add(Color.rgb(red, green, blue))
        }

        return intermediateColors
    }


    fun getColor(tilt: Float): Int {
        var x = 0
        if (tilt < 360f) {
            x = tilt.toInt() / 10
        } else {
            x = 35
        }
        return gradientColors[Math.abs(x)]
    }
}