package com.damonyuan.mvvmdemo.fragmentB

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.damonyuan.mvvmdemo.R
import com.damonyuan.mvvmdemo.rxbus.RxBus
import com.damonyuan.mvvmdemo.fragmenta.AViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_b.*

class FragmentB: Fragment() {
    private val vm = BViewModel()
    private val compositeDisposable = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_b, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.edit_text_b.setText(vm.getCustomerName())
        this.edit_text_b.addTextChangedListener(vm)

        /**
         * Synchronous solution
         */
//        compositeDisposable.add(
//            RxBus.toObservable()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe {
//                if (it.name == AViewModel::class.java.name) {
//                    val aViewModel = it as AViewModel
//                    if (aViewModel.getCustomerName() != vm.getCustomerName()) {
//                        vm.setCustomerName(aViewModel.getCustomerName())
//                        this.edit_text_b.setText(vm.getCustomerName())
//                    }
//                }
//            }
//        )

        /**
         * Asynchronous solution
         */
        compositeDisposable.add(RxBus
            .toObservable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (it.name == AViewModel::class.java.name) {
                    val aViewModel = it as AViewModel
                    if (aViewModel.getCustomerName() != vm.getCustomerName()) {
                        vm.setCustomerName(aViewModel.getCustomerName())
                    }
                } else if (it.name == BViewModel::class.java.name) {
                    if (this.edit_text_b.text.toString() != vm.getCustomerName()) {
                        this.edit_text_b.setText(vm.getCustomerName())
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