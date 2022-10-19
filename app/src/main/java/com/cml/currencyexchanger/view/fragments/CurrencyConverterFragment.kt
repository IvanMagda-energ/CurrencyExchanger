package com.cml.currencyexchanger.view.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.cml.currencyexchanger.data.models.Currency
import com.cml.currencyexchanger.databinding.FragmentCurrencyConverterBinding

class CurrencyConverterFragment :
    BaseFragment<FragmentCurrencyConverterBinding>(FragmentCurrencyConverterBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSpinners()
        observeAndSaveSpinners()
        observeAndSaveSellAmount()
        observeExchangeRates()
        observeReceiveAmount()

    }

    private fun observeReceiveAmount() {
        //TODO
    }


    private fun observeExchangeRates() {
        //TODO
    }


    private fun observeAndSaveSellAmount() {

        binding.currencyExchangeView.sellAmountEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                var sellObservableValue = "$text"
                //TODO: save livedata here
            }
            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }

    private fun observeAndSaveSpinners() {
        binding.currencyExchangeView.sellCurrencySpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    adapterView: AdapterView<*>?,
                    view: View?,
                    index: Int,
                    p3: Long
                ) {
                    val sellSelectedCurrency = Currency.values()[index]
                    //TODO: set livedata here
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    return
                }

            }
        binding.currencyExchangeView.receiveCurrencySpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    adapterView: AdapterView<*>?,
                    view: View?,
                    index: Int,
                    p3: Long
                ) {
                    val receiveSelectedCurrency = Currency.values()[index]
                    //TODO: set livedata here
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    return
                }

            }
    }

    private fun initSpinners() {
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            Currency.values()
        )
        binding.currencyExchangeView.sellCurrencySpinner.adapter = adapter
        binding.currencyExchangeView.receiveCurrencySpinner.adapter = adapter
    }

}