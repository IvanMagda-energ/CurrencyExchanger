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
                { _currentRatesLiveData.value = it },
                { Log.e("Local DB Error", "Error while observing Rates") })
            .also { disposable.add(it) }
    }

    private val _currentRatesLiveData by lazy { MutableLiveData<ExchangeRates>() }

    private val _sellAmountLiveData by lazy { MutableLiveData<Float>() }
    fun setSellAmount(amount: Float) {
        _sellAmountLiveData.value = amount
    }

    private val _receiveAmountLiveData by lazy { MutableLiveData<Float>() }
    val receiveAmountLiveData: LiveData<Float> get() = _receiveAmountLiveData

    private val _sellCurrencyLiveData by lazy { MutableLiveData<Currency>() }
    fun setSellCurrency(currency: Currency) {
        _sellCurrencyLiveData.value = currency
    }

    private val _receiveCurrencyLiveData by lazy { MutableLiveData<Currency>() }
    fun setReceiveCurrency(currency: Currency) {
        _receiveCurrencyLiveData.value = currency
    }

    override fun onCleared() {
        disposable.dispose()
    }


    @AssistedFactory
    interface Factory {
        fun create(savedStateHandle: SavedStateHandle): CurrencyConverterViewModel
    }
}