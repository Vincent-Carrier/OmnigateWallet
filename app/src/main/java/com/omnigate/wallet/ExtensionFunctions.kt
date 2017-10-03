package com.omnigate.wallet

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun <T> Single<T>.defaultSchedulers(): Single<T> {
	return this.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
}