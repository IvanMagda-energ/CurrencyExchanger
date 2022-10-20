package com.cml.currencyexchanger.view.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.cml.currencyexchanger.data.models.Currency
import com.cml.currencyexchanger.data.models.ExchangeRates
import com.cml.currencyexchanger.data.repositories.ExchangeRatesRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.reactivex.disposables.CompositeDisposable
import com.cml.currencyexchanger.Extensions.Companion.isPositive

class CurrencyConverterViewModel @AssistedInject constructor(
    @Assisted savedStateHandle: SavedStateHandle,
    exchangeRatesRepository: ExchangeRatesRepository
) : ViewModel() {

    private val disposable by lazy { CompositeDisposable() }

    init {
        exchangeRatesRepository.refreshExchangesRate()
            .subscribe()
            .also { disposable.add(it) }

        exchangeRatesRepository.observeRates()
            .subscribe(
                { currentRatesLiveData.value = it },
                { Log.e("Local DB Error", "Error while observing Rates") })
            .also { disposable.add(it) }
    }

    private val currentRatesLiveData by lazy { MutableLiveData<ExchangeRates>() }

    private val sellAmountLiveData by lazy { MutableLiveData<Float>() }
    fun setSellAmount(amount: Float) {
        sellAmountLiveData.value = amount
        setReceiveAmount()
    }

    private val _receiveAmountLiveData by lazy { MutableLiveData<Float>() }
    val receiveAmountLiveData: LiveData<Float> get() = _receiveAmountLiveData
    private fun setReceiveAmount() {
        val convertedValue = calculateReceiveAmount()
        convertedValue?.let {
            _receiveAmountLiveData.value = convertedValue
        }
    }

    private val sellCurrencyLiveData by lazy { MutableLiveData<Currency>() }
    fun setSellCurrency(currency: Currency) {
        sellCurrencyLiveData.value = currency
        setReceiveAmount()
    }

    private val receiveCurrencyLiveData by lazy { MutableLiveData<Currency>() }
    fun setReceiveCurrency(currency: Currency) {
        receiveCurrencyLiveData.value = currency
        setReceiveAmount()
    }

    private fun calculateReceiveAmount(): Float? {
        val inAmount = sellAmountLiveData.value?.toFloat() ?: 0.0F
        val inCurrency = sellCurrencyLiveData.value?.toString() ?: ""
        val outCurrency = receiveCurrencyLiveData.value?.toString() ?: ""
        return if (inAmount.isPositive() && inCurrency.isNotEmpty() && outCurrency.isNotEmpty()) {
            currentRatesLiveData.value?.convert(
                inAmount,
                inCurrency,
                outCurrency
            )
        } else null
    }


    override fun onCleared() {
        disposable.dispose()
    }


    @AssistedFactory
    interface Factory {
        fun create(savedStateHandle: SavedStateHandle): CurrencyConverterViewModel
    }
}