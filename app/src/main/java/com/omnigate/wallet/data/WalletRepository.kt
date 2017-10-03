package com.omnigate.wallet.data

import com.omnigate.wallet.models.Wallet
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers.mainThread
import org.jetbrains.anko.AnkoLogger

object WalletRepository : AnkoLogger {
	fun requestWallet(): Single<Wallet> {
		return api().requestWallet(AuthManager.apiKey)
				.observeOn(mainThread())
				.retry(3)
	}

	private fun api() = Retrofit.api
}