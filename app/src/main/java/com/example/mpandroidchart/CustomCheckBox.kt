package com.example.mpandroidchart

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.databinding.BindingAdapter
import com.example.mpandroidchart.databinding.CustomCurrencyCheckBoxBinding

class CustomCurrencyCheckBox(context: Context, attrs: AttributeSet?) :
    LinearLayoutCompat(context, attrs) {

    private var callback : CheckBoxCallback? = null

    fun setCallBack(listener: CheckBoxCallback) {
        callback = listener
    }

    private val binding =
        CustomCurrencyCheckBoxBinding.inflate(LayoutInflater.from(context), this, true)

    var checked: Boolean = true

    init {

        getCheckBox().setOnCheckedChangeListener { _, b ->
            checked = b
            callback!!.check()

        }
    }

    fun getCheckBox(): AppCompatCheckBox {
        return binding.checkBox
    }
}

interface CheckBoxCallback{
    fun check()
}

@BindingAdapter("setCurrencyTitle")
fun setCurrencyTitle(view: CustomCurrencyCheckBox, currency: String) {
    view.getCheckBox().text = currency
}

@BindingAdapter("setCurrencyICON")
fun setCurrencyIcon(view: CustomCurrencyCheckBox,resource: Int) {
    view.getCheckBox().setCompoundDrawablesWithIntrinsicBounds(resource, 0, 0, 0);
}
