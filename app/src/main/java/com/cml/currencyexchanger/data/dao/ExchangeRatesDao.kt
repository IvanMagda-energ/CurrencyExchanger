package com.cml.currencyexchanger.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.cml.currencyexchanger.data.models.ExchangeRates
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable

@Dao
abstract class ExchangeRatesDao {

    @Query("SELECT * FROM Exchange_Rates")
    abstract fun getExchangeRates(): Maybe<ExchangeRates>

    @Query("SELECT * FROM Exchange_Rates")
    abstract fun observeExchangeRates(): Observable<ExchangeRates>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(rates: ExchangeRates): Completable

    @Query("DELETE FROM Exchange_Rates")
    abstract fun clearTable(): Completable



}