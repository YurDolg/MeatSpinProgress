package com.dolgushin.yu.meetspinprogress

import android.util.TypedValue
import android.view.View


fun View.dpToPx(dp: Float) = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics())
fun View.dpToPxRound(dp: Float) = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics()))
fun View.spToPx(sp: Float) = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics())
fun View.spToPxRound(sp: Float) = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics()))
fun View.getCenterW() = this.width/2
fun View.getCenterH() = this.height/2
