package com.omnigate.wallet.data

import com.omnigate.wallet.models.Wallet
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

object WalletManager : AnkoLogger {
	private fun api() = OmnigateApi.api

	fun requestWallet(): Single<Wallet> {
		return api().requestWallet(AuthManager.apiKey!!)
				.observeOn(AndroidSchedulers.mainThread())
				.retry(3)
				.doFinally { info("requestWallet() ${AuthManager.apiKey}") }
	}
}