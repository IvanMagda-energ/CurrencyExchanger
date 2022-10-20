package com.cml.currencyexchanger.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cml.currencyexchanger.Extensions.Companion.roundDecimal


@Entity(tableName = "Exchange_Rates")
data class ExchangeRates(
    @PrimaryKey val base: String,
    val date: String,
    val rates: Map<String, Float>
) {

    fun convert(inAmount: Float, inCurrency: String, outCurrency: String): Float {
        if((inCurrency == outCurrency) || inAmount == 0.0F) return 0.0F
        return if(inCurrency == base) convertFromBase(inAmount, outCurrency).roundDecimal()
        else {
            val baseAmount = getBaseAmount(inAmount, inCurrency)
            convertFromBase(baseAmount, outCurrency).roundDecimal()
        }
    }

    private fun getBaseAmount(inAmount: Float, inCurrency: String): Float {
        val inCurrencyBaseRate = rates[inCurrency] ?: 0.0F
        return if(inCurrencyBaseRate > 0) inAmount / inCurrencyBaseRate
            else 0.0F
    }

    private fun convertFromBase(baseAmount: Float, outCurrency: String): Float =
        baseAmount * (rates[outCurrency] ?: 0.0F)

}