package com.cml.currencyexchanger.view.popup

import android.content.Context
import com.cml.currencyexchanger.R

class PopupMessage(context: Context) : PopupMessageBuilder(context) {

    fun showEmptyInAmountFailurePopup() {
        PopupMessage(context).createFromBasicContentView(
            title = context.getString(R.string.ops),
            msg = context.getString(R.string.empty_in_amount),
            positive = context.getString(R.string.ok)
        ).withIcon(R.drawable.ic_warning)
    }

    fun showWrongCurrencyFailurePopup() {
        PopupMessage(context).createFromBasicContentView(
            title = context.getString(R.string.ops),
            msg = context.getString(R.string.wrong_currency_choice),
            positive = context.getString(R.string.ok)
        ).withIcon(R.drawable.ic_warning)
    }

    fun showNoSelectedBalanceFailurePopup() {
        PopupMessage(context).createFromBasicContentView(
            title = context.getString(R.string.ops),
            msg = context.getString(R.string.no_selected_account),
            positive = context.getString(R.string.ok)
        ).withIcon(R.drawable.ic_warning)
    }

    fun showNoMoneyFailurePopup() {
        PopupMessage(context).createFromBasicContentView(
            title = context.getString(R.string.ops),
            msg = context.getString(R.string.no_money),
            positive = context.getString(R.string.ok)
        ).withIcon(R.drawable.ic_warning)
    }

    fun showConversionSuccessPopup(
        inAmount: String,
        inCurrency: String,
        outAmount: String,
        outCurrency: String,
        fee: String
    ) {
        PopupMessage(context).createFromBasicContentView(
            title = context.getString(R.string.success),
            msg = context.getString(
                R.string.success_msg, inAmount, inCurrency, outAmount, outCurrency, fee
            ),
            positive = context.getString(R.string.ok)
        ).withIcon(R.drawable.ic_done)
    }

}