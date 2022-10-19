package com.cml.currencyexchanger.data.repositories

import com.cml.currencyexchanger.data.dao.ExchangeRatesDao
import com.cml.currencyexchanger.data.models.ExchangeRates
import com.cml.currencyexchanger.data.sources.ExchangeRatesSource
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ExchangeRatesRepository @Inject constructor(
    private val exchangeRatesDao: ExchangeRatesDao,
    private val exchangeRatesSource: ExchangeRatesSource
) {

    companion object {
        private const val EXCHANGE_RATES_REFRESH_AT = 5L
    }

    fun refreshExchangesRate(): Observable<ExchangeRates> {
        return Observable.interval(EXCHANGE_RATES_REFRESH_AT, TimeUnit.SECONDS)
            .switchMap {
                exchangeRatesSource.getExchangeRates().toObservable().subscribeOn(Schedulers.io())
            }
            .switchMap {
                exchangeRatesDao.clearTable()
                    .andThen(exchangeRatesDao.insert(it))
                    .toObservable<ExchangeRates>()
                    .subscribeOn(Schedulers.io())
            }
            .subscribeOn(Schedulers.io())
    }

    fun observeRates(): Observable<ExchangeRates> {
        return exchangeRatesDao.observeExchangeRates()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}