package com.dolgushin.yu.meetspinprogress

import android.util.Log

class FlyingMeats() {
    var calculate = false
    var meats = ArrayList<Meat>()

    init {
        meats = arrayListOf(Meat(), Meat(), Meat(), Meat(), Meat(), Meat(),Meat())
    }

    private var oneMeatSizeWidth = 0f
    var oneMeatSizeHeight = 0f
    private var ballsRadius = 0f
    var betweenDistance = 50f
    var quantity = 7
    fun calculateMeats(viewWidth: Int, viewHeight: Int) {
        if (calculate) return
        oneMeatSizeWidth = (viewWidth - (betweenDistance * (quantity - 1))) / quantity.toFloat()
        ballsRadius = Math.min(viewHeight, viewWidth) / 8f

        oneMeatSizeHeight = ballsRadius / 1.5f
        for (i in 0 until meats.size) {
            meats[i].width = oneMeatSizeWidth
            meats[i].height = oneMeatSizeHeight
            meats[i].viewWidth = viewWidth
            meats[i].viewHeight = viewHeight
            meats[i].ballRadius = ballsRadius

            meats[i].trunk.left = ballsRadius + (oneMeatSizeWidth - ballsRadius + betweenDistance) * i
            meats[i].trunk.right = meats[i].trunk.left + (oneMeatSizeWidth - ballsRadius)
            meats[i].trunk.top = viewHeight / 2 - oneMeatSizeHeight
            meats[i].trunk.bottom = viewHeight / 2 + oneMeatSizeHeight
            meats[i].ballOneCenter.x = meats[i].trunk.left
            meats[i].ballOneCenter.y = viewHeight / 2 + ballsRadius
            meats[i].ballTwoCenter.x = meats[i].trunk.left
            meats[i].ballTwoCenter.y = viewHeight / 2 - ballsRadius

            meats[i].originEndPosition = meats[i].trunk.left + (oneMeatSizeWidth - ballsRadius)
            meats[i].originStartPosition = meats[i].trunk.left
        }
        calculate = true
    }

    fun updateMeatsPosition(delta: Float) {
        for (i in 0 until meats.size) {
            meats[i].trunk.left = ballsRadius + (oneMeatSizeWidth - ballsRadius + betweenDistance) * i + delta
            meats[i].trunk.right = meats[i].trunk.left + (oneMeatSizeWidth - ballsRadius)
            meats[i].ballOneCenter.x = meats[i].trunk.left
            meats[i].ballTwoCenter.x = meats[i].trunk.left
            meats[i].updatePositions(delta-betweenDistance)
        }

    }
}