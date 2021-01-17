package com.chen.baseextend.repos.viewmodel

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 *  Created by 86152 on 2020-03-22
 **/
open class TimerViewModel : MainViewModel() {

    private val timerObservable by lazy { Observable.interval(1, TimeUnit.SECONDS) }

    fun obs(t: ((timer: Long) -> Unit)) {
        timerObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(t).run { compositeDisposable.add(this) }
    }
}