package com.cml.currencyexchanger.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.cml.currencyexchanger.data.dao.ExchangeRatesDao
import com.cml.currencyexchanger.data.models.ExchangeRates

@Database(entities = [ExchangeRates::class], version = 1)
@TypeConverters(ListMapStringFloatConverter::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getExchangeRatesDao(): ExchangeRatesDao
}