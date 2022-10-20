package com.cml.currencyexchanger.data.models

import com.cml.currencyexchanger.Extensions.Companion.toStringOrEmpty
import com.cml.currencyexchanger.data.utils.IValidator

data class Balance(
    val euro: Float = 1000.0f,
    val usd: Float = 0.0f,
    val bgn: Float = 0.0f,
): IValidator {

    companion object {
        const val COMMISSION_PERCENT = 0.7f
        const val FREE_CONVERSIONS = 5
    }

    private fun asMap(): Map<Currency, Float> {
        return mapOf(Currency.EUR to euro, Currency.USD to usd, Currency.BGN to bgn)
    }

    fun findBalance(currency: String): Float {
        val m = asMap()
        return asMap().entries.find { (key, value) ->
            key.toStringOrEmpty() == currency
        }?.value ?: 0.0f
    }

    override fun isAmountNotEmpty(sellAmount: Float): Boolean {
        return sellAmount > 0.0f
    }

    override fun areCurrenciesSame(currency1: String, currency2: String): Boolean {
        return currency1 == currency2
    }

    override fun areEnoughFunds(
        balanceAmount: Float,
        sellAmount: Float,
        commission: Float
    ): Boolean =
        balanceAmount > sellAmount + commission

}