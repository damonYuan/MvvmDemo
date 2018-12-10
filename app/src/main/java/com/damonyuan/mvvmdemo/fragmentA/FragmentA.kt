package com.damonyuan.mvvmdemo.fragmenta

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.damonyuan.mvvmdemo.ICustomerName
import com.damonyuan.mvvmdemo.fragmentB.BViewModel
import com.damonyuan.mvvmdemo.R
import com.damonyuan.mvvmdemo.fragmentA.AViewModel
import com.damonyuan.mvvmdemo.rxbus.RxBus
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_a.*
import kotlinx.android.synthetic.main.fragment_b.*
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class FragmentA: Fragment() {
    private val vm = AViewModel()
    private val compositeDisposable = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_a, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.edit_text_a.setText(vm.getCustomerName())
        this.edit_text_a.addTextChangedListener(vm)

        compositeDisposable.add(
            RxBus.toObservable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (it is ICustomerName) {
                    val customerName = it as ICustomerName
                    if (customerName.getCustomerName() != vm.getCustomerName()) {
                        this.edit_text_b.setText(customerName.getCustomerName())
                    }
                }
            }
        )
    }

    override fun onDestroy() {
        this.edit_text_b.removeTextChangedListener(vm)
        compositeDisposable.dispose()
        super.onDestroy()
    }
}