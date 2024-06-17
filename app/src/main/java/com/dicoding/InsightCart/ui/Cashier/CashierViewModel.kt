package com.dicoding.InsightCart.ui.Cashier

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CashierViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Cashier Fragment"
    }
    val text: LiveData<String> = _text
}