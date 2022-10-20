package com.cml.currencyexchanger.view.popup

import android.content.Context
import android.view.View
import android.widget.TextView
import com.cml.currencyexchanger.Extensions.Companion.makeGone
import com.cml.currencyexchanger.Extensions.Companion.makeVisible
import com.cml.currencyexchanger.databinding.PopupMsgBinding

open class PopupMessageBuilder(context: Context) : BaseDialog(context) {

    lateinit var binding: PopupMsgBinding

    protected fun createFromBasicContentView(
        title: String = "",
        msg: String = "",
        positive: String = "",
        positiveListener: View.OnClickListener? = null
    ): PopupMessageBuilder {
        binding = PopupMsgBinding.inflate(layoutInflater)
        wrapToContentSize()
        setContentView(binding.root)

        binding.icon.makeGone()
        handleTextView(title, binding.topPopupTitle)
        handleTextView(msg, binding.topPopupMessage)
        if (title.isNotBlank()) binding.topLayout.makeVisible() else binding.topLayout.makeGone()

        if (positive.isBlank()) binding.popupOkButton.makeGone()
        else {
            binding.popupOkButton.makeVisible()
            binding.popupOkButton.text = positive
            binding.popupOkButton.setOnClickListener { v: View? ->
                positiveListener?.onClick(v)
                dismiss()
            }
        }
        show()
        return this
    }

    private fun handleTextView(textValue: String = "", textView: TextView) {
        if (textValue.isNotBlank()) {
            textView.text = textValue
            textView.makeVisible()
        } else {
            textView.makeGone()
        }
    }

    fun withIcon(iconRes: Int): PopupMessageBuilder {
        binding.icon.makeVisible()
        binding.icon.setImageResource(iconRes)
        return this
    }


}