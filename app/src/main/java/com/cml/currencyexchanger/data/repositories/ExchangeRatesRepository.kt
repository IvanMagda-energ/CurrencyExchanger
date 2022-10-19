package com.cml.currencyexchanger.data.repositories

import com.cml.currencyexchanger.data.dao.ExchangeRatesDao
import com.cml.currencyexchanger.data.models.ExchangeRates
import com.cml.currencyexchanger.data.sources.ExchangeRatesSource
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ExchangeRatesRepository @Inject constructor(
    private val exchangeRatesDao: ExchangeRatesDao,
    private val exchangeRatesSource: ExchangeRatesSource
) {

    fun refreshExchangesRate(): Completable {
        return exchangeRatesSource.getExchangeRates()
            .flatMapCompletable {
                exchangeRatesDao.clearTable()
                    .andThen(exchangeRatesDao.insert(it))
                    .subscribeOn(Schedulers.io())
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun observeRates(): Observable<ExchangeRates> {
        return exchangeRatesDao.observeExchangeRates()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}