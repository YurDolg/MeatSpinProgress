package com.dolgushin.yu.meetspinprogress

import android.util.Log

class FlyingHorizontalMeats() {
    private val gayColors = ColorsManager()
    var calculate = false
    var meats = ArrayList<Meat>()
    var betweenDistance = 0f
        set(value) {
            field = value
            calculate = false
        }
    var quantity = 0
        set(value) {
            field = value
            meats.clear()
            for (i in 1..field) {
                meats.add(Meat())
            }
            calculate = false
        }
    private var oneMeatSizeWidth = 0f
    var oneMeatSizeHeight = 0f
    private var ballsRadius = 0f
    fun calculateMeats(viewWidth: Int, viewHeight: Int) {
        if (calculate) return
        oneMeatSizeWidth = viewWidth / quantity.toFloat()
        ballsRadius = Math.min(viewHeight, viewWidth) / 8f
        var oneTrunkWidth = oneMeatSizeWidth - ballsRadius - betweenDistance
        oneMeatSizeHeight = ballsRadius / 1.5f
        for (i in 0 until meats.size) {
            meats[i].width = oneMeatSizeWidth
            meats[i].height = oneMeatSizeHeight
            meats[i].viewWidth = viewWidth
            meats[i].viewHeight = viewHeight
            meats[i].ballRadius = ballsRadius
            meats[i].color = gayColors.getColor()
            meats[i].trunk.left = ballsRadius + oneMeatSizeWidth * i
            meats[i].trunk.right = meats[i].trunk.left + oneTrunkWidth
            meats[i].trunk.top = viewHeight / 2 - oneMeatSizeHeight
            meats[i].trunk.bottom = viewHeight / 2 + oneMeatSizeHeight
            meats[i].ballOneCenter.x = meats[i].trunk.left
            meats[i].ballOneCenter.y = viewHeight / 2 + ballsRadius
            meats[i].ballTwoCenter.x = meats[i].trunk.left
            meats[i].ballTwoCenter.y = viewHeight / 2 - ballsRadius

            meats[i].originEndPosition = meats[i].trunk.right
            meats[i].originStartPosition = meats[i].trunk.left
        }
        gayColors.reset()
        calculate = true
    }

    fun updateMeatsPosition(delta: Float) {
        for (i in 0 until meats.size) {
            var oneTrunkWidth = oneMeatSizeWidth - ballsRadius - betweenDistance
            meats[i].trunk.left = ballsRadius + oneMeatSizeWidth * i + delta
            meats[i].trunk.right = meats[i].trunk.left + oneTrunkWidth
            meats[i].ballOneCenter.x = meats[i].trunk.left
            meats[i].ballTwoCenter.x = meats[i].trunk.left
            meats[i].updatePositions(delta)
        }

    }
}