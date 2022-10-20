package com.cml.currencyexchanger.view.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.cml.currencyexchanger.Extensions.Companion.toFloatOrZero
import com.cml.currencyexchanger.Extensions.Companion.toLongOrZero
import com.cml.currencyexchanger.Extensions.Companion.toStringOrEmpty
import com.cml.currencyexchanger.data.models.Balance
import com.cml.currencyexchanger.data.models.Conversion
import com.cml.currencyexchanger.data.models.Currency
import com.cml.currencyexchanger.data.models.ExchangeRates
import com.cml.currencyexchanger.data.repositories.ExchangeRatesRepository
import com.cml.currencyexchanger.data.repositories.UserRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.reactivex.disposables.CompositeDisposable

class CurrencyConverterViewModel @AssistedInject constructor(
    @Assisted savedStateHandle: SavedStateHandle,
    exchangeRatesRepository: ExchangeRatesRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val disposable by lazy { CompositeDisposable() }

    init {
        exchangeRatesRepository.refreshExchangesRate()
            .subscribe(
                {},
                { Log.e("Refresh Rates Error", "Error while refreshing rates: $it") }
            )
            .also { disposable.add(it) }

        userRepository.createDefaultUserIfNotExists()
            .subscribe(
                { Log.i("Created user", "$it") },
                { Log.e("Create default User Error", "$it") },
                {}
            )
            .also { disposable.add(it) }

        exchangeRatesRepository.observeRates()
            .subscribe(
                { currentRatesLiveData.value = it },
                { Log.e("Local DB Error", "Error while observing Rates") })
            .also { disposable.add(it) }

        userRepository.observeUser()
            .subscribe(
                {
                    _balanceLiveData.value = it.balance
                    userConversionsAmountLiveData.value = it.conversionsAmount
                },
                { Log.e("Local DB Error", "Error while observing Rates") })
            .also { disposable.add(it) }


    }

    private val currentRatesLiveData by lazy { MutableLiveData<ExchangeRates>() }
    private val calculatedCommission by lazy { MutableLiveData<Float>() }
    private val userConversionsAmountLiveData by lazy { MutableLiveData<Long>() }

    private val sellAmountLiveData by lazy { MutableLiveData<Float>() }
    fun setSellAmount(amount: Float) {
        sellAmountLiveData.value = amount
        setReceiveAmount()
    }

    private val _balanceLiveData by lazy { MutableLiveData<Balance>() }
    val balanceLiveData: LiveData<Balance> get() = _balanceLiveData


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

    private val _conversionLiveData by lazy { MutableLiveData<Conversion>() }
    val conversionLiveData: LiveData<Conversion> get() = _conversionLiveData

    private fun calculateReceiveAmount(): Float? {
        val inAmount = sellAmountLiveData.value.toFloatOrZero()
        val inCurrency = sellCurrencyLiveData.value.toStringOrEmpty()
        val outCurrency = receiveCurrencyLiveData.value.toStringOrEmpty()
        return if (inCurrency.isNotEmpty() && outCurrency.isNotEmpty()) {
            currentRatesLiveData.value?.convert(
                inAmount,
                inCurrency,
                outCurrency
            )
        } else null
    }

    fun isAmountNotEmpty(): Boolean {
        _balanceLiveData.value?.let {
            return it.isAmountNotEmpty(sellAmountLiveData.value.toFloatOrZero())
        }
        return true
    }

    fun areCurrenciesTheSame(): Boolean {
        _balanceLiveData.value?.let {
            return it.areCurrenciesSame(
                sellCurrencyLiveData.value.toStringOrEmpty(),
                receiveCurrencyLiveData.value.toStringOrEmpty()
            )
        }
        return true
    }

    private fun calcRealCommission(amount: Float): Float {
        return _balanceLiveData.value?.calcCommission(
            amount,
            userConversionsAmountLiveData.value.toLongOrZero()
        ) ?: 0.0f
    }

    fun areEnoughFunds(): Boolean {
        calculatedCommission.value = calcRealCommission(sellAmountLiveData.value.toFloatOrZero())
        _balanceLiveData.value?.let {
            return it.areEnoughFunds(
                it.findBalanceValue(sellCurrencyLiveData.value.toStringOrEmpty()),
                sellAmountLiveData.value.toFloatOrZero(),
                calculatedCommission.value.toFloatOrZero()
            )
        }
        return false
    }

    fun convert() {
        calculatedCommission.value = calcRealCommission(sellAmountLiveData.value.toFloatOrZero())
        _balanceLiveData.value?.let { balance ->
            val conversion = Conversion(
                sellCurrencyLiveData.value.toStringOrEmpty(),
                receiveCurrencyLiveData.value.toStringOrEmpty(),
                sellAmountLiveData.value.toFloatOrZero(),
                _receiveAmountLiveData.value.toFloatOrZero(),
                calculatedCommission.value.toFloatOrZero(),
                balance
            )
            userRepository.convert(
                conversion
            ).subscribe(
                { _conversionLiveData.value = conversion },
                { Log.e("Conversion Error!", "$it") }
            )
                .also { disposable.add(it) }
        }

    }


    override fun onCleared() {
        disposable.dispose()
    }


    @AssistedFactory
    interface Factory {
        fun create(savedStateHandle: SavedStateHandle): CurrencyConverterViewModel
    }
}