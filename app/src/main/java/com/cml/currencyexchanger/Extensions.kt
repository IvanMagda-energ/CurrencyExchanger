package com.cml.currencyexchanger

import java.math.RoundingMode
import java.text.DecimalFormat

class Extensions {
    companion object {
        fun Float.isPositive(): Boolean = this.compareTo(0.0F) > 0
        fun Float.roundDecimal(): Float =
            DecimalFormat("#.##")
                .apply { roundingMode = RoundingMode.FLOOR }
                .format(this).toFloatOrNull() ?: 0.0F


    }
}