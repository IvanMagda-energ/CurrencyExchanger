package com.cml.currencyexchanger.data.repositories

import com.cml.currencyexchanger.data.dao.ExchangeRatesDao
import com.cml.currencyexchanger.data.sources.ExchangeRatesSource
import javax.inject.Inject

class ExchangeRatesRepository @Inject constructor(
    private val exchangeRatesDao: ExchangeRatesDao,
    private val exchangeRatesSource: ExchangeRatesSource
) {

    //TODO: getExchangesRates and refresh local db
}