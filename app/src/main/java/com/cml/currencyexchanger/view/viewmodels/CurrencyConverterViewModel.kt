package com.cml.currencyexchanger.view.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.cml.currencyexchanger.data.models.Currency
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class CurrencyConverterViewModel @AssistedInject constructor(
    @Assisted savedStateHandle: SavedStateHandle
) : ViewModel() {


    private val _sellAmountLiveData by lazy { MutableLiveData<Float>() }
    val sellAmountLiveData: LiveData<Float> get() = _sellAmountLiveData

    fun setSellAmount(amount: Float) {
        _sellAmountLiveData.value = amount
    }

    private val _receiveAmountLiveData by lazy { MutableLiveData<Float>() }
    val receiveAmountLiveData: LiveData<Float> get() = _receiveAmountLiveData

    fun setReceiveAmount(amount: Float) {
        _receiveAmountLiveData.value = amount
    }

    private val _sellCurrencyLiveData by lazy { MutableLiveData<Currency>() }
    val sellCurrencyLiveData: LiveData<Currency> get() = _sellCurrencyLiveData

    fun setSellCurrency(currency: Currency) {
        _sellCurrencyLiveData.value = currency
    }

    private val _receiveCurrencyLiveData by lazy { MutableLiveData<Currency>() }
    val receiveCurrencyLiveData: LiveData<Currency> get() = _receiveCurrencyLiveData

    fun setReceiveCurrency(currency: Currency) {
        _receiveCurrencyLiveData.value = currency
    }


    @AssistedFactory
    interface Factory {
        fun create(savedStateHandle: SavedStateHandle): CurrencyConverterViewModel
    }
}