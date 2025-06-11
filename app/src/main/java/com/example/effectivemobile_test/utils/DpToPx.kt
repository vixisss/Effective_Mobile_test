package com.example.effectivemobile_test.utils

import android.content.Context
import android.util.TypedValue

object  DpToPx {
    fun dpToPx(dp: Float, context: Context): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context.resources.displayMetrics
        ).toInt()
    }
}