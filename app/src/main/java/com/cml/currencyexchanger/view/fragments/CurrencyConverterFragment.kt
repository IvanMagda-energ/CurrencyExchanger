package com.cml.currencyexchanger.view.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.cml.currencyexchanger.App
import com.cml.currencyexchanger.Extensions.Companion.isPositive
import com.cml.currencyexchanger.Extensions.Companion.roundDecimal
import com.cml.currencyexchanger.data.models.Currency
import com.cml.currencyexchanger.databinding.FragmentCurrencyConverterBinding
import com.cml.currencyexchanger.view.popup.PopupMessage
import com.cml.currencyexchanger.view.uiUtils.lazyViewModel
import com.cml.currencyexchanger.view.viewmodels.CurrencyConverterViewModel

class CurrencyConverterFragment :
    BaseFragment<FragmentCurrencyConverterBinding>(FragmentCurrencyConverterBinding::inflate) {


    private val viewModel: CurrencyConverterViewModel by lazyViewModel {
        App.get().components().getAppComponent().getCurrencyConverterViewModelFactory().create(it)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSpinners()
        watchSpinners()
        watchSellAmount()
        observeReceiveAmount()
        observeBalances()
        onSubmitButtonClick()
        observeConversion()
    }

    private fun observeConversion() {
        viewModel.conversionLiveData.observe(viewLifecycleOwner) {
            PopupMessage(requireContext()).showConversionSuccessPopup(
                it.fromAmount.toString(),
                it.fromCurrency,
                it.toAmount.toString(),
                it.toCurrency,
                it.commission.toString()
            )
        }
    }

    private fun onSubmitButtonClick() {
        binding.submitButton.setOnClickListener {
            attemptConversion()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun observeReceiveAmount() {
        viewModel.receiveAmountLiveData.observe(viewLifecycleOwner) {
            if (it.isPositive()) binding.currencyExchangeView.receiveAmountTextView.text = "+ $it"
            else binding.currencyExchangeView.receiveAmountTextView.text = ""
        }
    }

    private fun observeBalances() {
        viewModel.balanceLiveData.observe(viewLifecycleOwner) {
            binding.balancesView.balanceEurValue.text = "${it.euro.roundDecimal()}"
            binding.balancesView.balanceUsdValue.text = "${it.usd.roundDecimal()}"
            binding.balancesView.balanceBgnValue.text = "${it.bgn.roundDecimal()}"
        }
    }


    private fun watchSellAmount() {

        binding.currencyExchangeView.sellAmountEditText.addTextChangedListener(object :
            TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.setSellAmount(text.toString().toFloatOrNull() ?: 0.0F)
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }

    private fun watchSpinners() {
        binding.currencyExchangeView.sellCurrencySpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    adapterView: AdapterView<*>?,
                    view: View?,
                    index: Int,
                    p3: Long
                ) {
                    viewModel.setSellCurrency(Currency.values()[index])
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
                    viewModel.setReceiveCurrency(Currency.values()[index])
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

    private fun attemptConversion() {
        if (!viewModel.isAmountNotEmpty()) {
            PopupMessage(requireContext()).showEmptyInAmountFailurePopup()
            return
        }
        if (viewModel.areCurrenciesTheSame()) {
            PopupMessage(requireContext()).showWrongCurrencyFailurePopup()
            return
        }
        if (!viewModel.areEnoughFunds()) {
            PopupMessage(requireContext()).showNoMoneyFailurePopup()
            return
        }
        viewModel.convert()
    }

}