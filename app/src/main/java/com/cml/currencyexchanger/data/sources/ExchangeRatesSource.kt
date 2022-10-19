package com.cml.currencyexchanger.data.sources

import com.cml.currencyexchanger.data.api.ExchangeRatesApi
import com.cml.currencyexchanger.data.models.ExchangeRates
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExchangeRatesSource @Inject constructor(
    private val exchangeRatesApi: ExchangeRatesApi
) {

    fun getExchangeRates(): Single<ExchangeRates> {
        return exchangeRatesApi.getExchangeRates()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}