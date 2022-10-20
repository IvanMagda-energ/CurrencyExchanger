package com.cml.currencyexchanger.data.models

import com.cml.currencyexchanger.Extensions.Companion.toFloatOrZero
import com.cml.currencyexchanger.Extensions.Companion.toStringOrEmpty
import com.cml.currencyexchanger.data.utils.IValidator

data class Balance(
    val euro: Float = 1000.0f,
    val usd: Float = 0.0f,
    val bgn: Float = 0.0f,
) : IValidator {

    companion object {
        private const val COMMISSION_PERCENT = 0.07f
        private const val FREE_CONVERSIONS = 5
    }

    private fun asMap(): Map<Currency, Float> {
        return mapOf(Currency.EUR to euro, Currency.USD to usd, Currency.BGN to bgn)
    }

    private fun findBalanceMap(currency: String): Map.Entry<Currency, Float>? {
        return asMap().entries.find { (key, value) ->
            key.toStringOrEmpty() == currency
        }
    }

    fun findBalanceValue(currency: String): Float {
        return findBalanceMap(currency)?.value ?: 0.0f
    }

    fun incrementBalance(currency: String, amount: Float): Balance? {
        val currencyLocal = findBalanceMap(currency)?.key
        currencyLocal?.let {
            return when (currencyLocal) {
                Currency.EUR -> Balance(euro = euro + amount, usd, bgn)
                Currency.USD -> Balance(euro = euro, usd + amount, bgn)
                Currency.BGN -> Balance(euro = euro, usd, bgn + amount)
            }
        }
        return null
    }

    fun decrementBalance(currency: String, amount: Float): Balance? {
        val currencyLocal = findBalanceMap(currency)?.key
        currencyLocal?.let {
            return when (currencyLocal) {
                Currency.EUR -> Balance(euro = euro - amount, usd, bgn)
                Currency.USD -> Balance(euro = euro, usd - amount, bgn)
                Currency.BGN -> Balance(euro = euro, usd, bgn - amount)
            }
        }
        return null
    }

    //Here is the place to create the special conditions for the commission
    fun calcCommission(amount: Float, numberUserConversions: Long): Float {
        val commissionByPercent = amount * COMMISSION_PERCENT
        return if (numberUserConversions > FREE_CONVERSIONS)
            commissionByPercent.toFloatOrZero()
        else 0.0f
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