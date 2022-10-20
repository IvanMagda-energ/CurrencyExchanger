package com.cml.currencyexchanger

class Extensions {
    companion object {
        fun Float.isPositive(): Boolean = this.compareTo(0.0F) > 0
    }
}