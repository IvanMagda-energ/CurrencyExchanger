package com.cml.currencyexchanger.data.models

data class Conversion(
    val fromCurrency: String,
    val toCurrency: String,
    val fromAmount: Float,
    val toAmount: Float,
    val commission: Float,
    val balance: Balance
)
