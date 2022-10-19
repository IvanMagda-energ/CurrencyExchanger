package com.cml.currencyexchanger.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Exchange_Rates")
data class ExchangeRates(
    @PrimaryKey val base: String,
    val date: String,
    val rates: List<Map<String, Float>>
)