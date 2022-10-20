package com.cml.currencyexchanger.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.nio.channels.FileLock


@Entity(tableName = "Exchange_Rates")
data class ExchangeRates(
    @PrimaryKey val base: String,
    val date: String,
    val rates: Map<String, Float>
) {

    fun convert(inAmount: Float, inCurrency: String, outCurrency: String): Float? {
        //TODO: implement
        return 100.0F
    }
}