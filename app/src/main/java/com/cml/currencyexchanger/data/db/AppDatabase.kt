package com.cml.currencyexchanger.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.cml.currencyexchanger.data.dao.ExchangeRatesDao
import com.cml.currencyexchanger.data.dao.UserDao
import com.cml.currencyexchanger.data.models.ExchangeRates
import com.cml.currencyexchanger.data.models.User

@Database(entities = [ExchangeRates::class, User::class], version = 2)
@TypeConverters(MapStringFloatConverter::class, BalanceConverter::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getExchangeRatesDao(): ExchangeRatesDao
    abstract fun getUserDao(): UserDao
}