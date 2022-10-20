package com.cml.currencyexchanger.data.utils

interface IValidator {
    fun isAmountNotEmpty(sellAmount: Float): Boolean
    fun areCurrenciesSame(currency1: String, currency2: String): Boolean
    fun areEnoughFunds(balanceAmount: Float, sellAmount: Float, commission: Float): Boolean
}