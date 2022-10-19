package com.cml.currencyexchanger.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.cml.currencyexchanger.data.models.ExchangeRates
import io.reactivex.Completable
import io.reactivex.Maybe

@Dao
abstract class ExchangeRatesDao {

    @Query("SELECT * FROM Exchange_Rates LIMIT 1")
    abstract fun getExchangeRates(): Maybe<ExchangeRates>

    @Transaction
    open fun insertSingleRates(rates: ExchangeRates) {
        clearTable()
        insert(rates)
    }

    fun insertExchangeRatesCompletable(rates: ExchangeRates): Completable {
        insertSingleRates(rates)
        return Completable.complete()
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(rates: ExchangeRates): Completable

    @Query("DELETE FROM Exchange_Rates")
    abstract fun clearTable(): Completable



}