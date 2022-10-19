package com.cml.currencyexchanger.data.api

import com.cml.currencyexchanger.data.models.ExchangeRates
import io.reactivex.Single
import retrofit2.http.GET

interface ExchangeRatesApi {

    @GET("tasks/api/currency-exchange-rates")
    fun getExchangeRates(): Single<ExchangeRates>
}