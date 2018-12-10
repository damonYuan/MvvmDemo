package com.damonyuan.mvvmdemo.fragmentB

import android.text.Editable
import android.text.TextWatcher
import com.damonyuan.mvvmdemo.ICustomerName
import com.damonyuan.mvvmdemo.rxbus.RxBus
import com.damonyuan.mvvmdemo.rxbus.RxEvent

class BViewModel: TextWatcher, RxEvent, ICustomerName {
    override val name: String
        get() = BViewModel::class.java.name

    private var mCustomerName = ""

    override fun afterTextChanged(s: Editable?) {
        // Only fire the event when data is updated
        if (s.toString() != mCustomerName) {
            mCustomerName = s.toString()
            RxBus.send(this)
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    override fun getCustomerName(): String {
        return mCustomerName
    }

    fun setCustomerName(customerName: String) {
        // Only fire the event when data is updated
        if (customerName != mCustomerName) {
            mCustomerName = customerName
            RxBus.send(this)
        }
    }
}