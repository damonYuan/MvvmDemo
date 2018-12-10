package com.damonyuan.mvvmdemo.rxbus

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * Created by damon on 6/24/18.
 */

object RxBus {
    private val mBus = PublishSubject.create<RxEvent>()

    fun send(event: RxEvent) {
        mBus.onNext(event)
    }

    fun toObservable(): Observable<RxEvent> {
        return mBus
    }

    fun hasObservers(): Boolean {
        return mBus.hasObservers()
    }
}