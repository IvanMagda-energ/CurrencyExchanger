package com.cml.currencyexchanger.view.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cml.currencyexchanger.data.models.Currency

class CurrencyConverterViewModel : ViewModel() {


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
}