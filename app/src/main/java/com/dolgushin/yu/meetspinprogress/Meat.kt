package com.dolgushin.yu.meetspinprogress

import android.graphics.PointF
import android.graphics.RectF

class Meat() {
    var viewWidth = 0
    var viewHeight = 0
    var originStartPosition = 0f
    var originEndPosition = 0f

    var width = 0f
    var height = 0f
    var trunk = RectF()
    var ballOneCenter = PointF()
    var ballTwoCenter = PointF()
    var ballRadius = 0f

    var secondaryTrunk: RectF = RectF(0f, 0f, 0f, 0f)
    var ballTwoSecondaryCenter: PointF = PointF(0f, 0f)
    var ballOneSecondaryCenter: PointF = PointF(0f, 0f)
    fun updatePositions(delta: Float) {
        if (trunk.left > viewWidth) {
            secondaryTrunk.left = trunk.left - viewWidth
        } else {
            secondaryTrunk.left = 0f
        }


        if (trunk.right > viewWidth) {
            secondaryTrunk.right = trunk.right - viewWidth
        } else {
            secondaryTrunk.right = 0f
        }
        if (ballOneCenter.x > viewWidth) {
            ballOneSecondaryCenter.x = ballOneCenter.x - viewWidth
            ballTwoSecondaryCenter.x = ballOneCenter.x - viewWidth
        } else {
            ballOneSecondaryCenter.x = -ballRadius
            ballTwoSecondaryCenter.x = -ballRadius
        }
        ballTwoSecondaryCenter.y = ballTwoCenter.y
        ballOneSecondaryCenter.y = ballOneCenter.y
        secondaryTrunk.top = trunk.top
        secondaryTrunk.bottom = trunk.bottom
    }
}
