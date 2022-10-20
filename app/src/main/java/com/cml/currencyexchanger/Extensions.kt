package com.cml.currencyexchanger

import android.opengl.Visibility
import android.view.View
import java.math.RoundingMode
import java.text.DecimalFormat

class Extensions {
    companion object {
        fun Float.isPositive(): Boolean = this.compareTo(0.0F) > 0
        fun Float.roundDecimal(): Float =
            DecimalFormat("#.##")
                .apply { roundingMode = RoundingMode.FLOOR }
                .format(this).toFloatOrNull() ?: 0.0F

        fun View.makeGone() {
            this.visibility = View.GONE
        }

        fun View.makeVisible() {
            this.visibility = View.VISIBLE
        }

    }
}