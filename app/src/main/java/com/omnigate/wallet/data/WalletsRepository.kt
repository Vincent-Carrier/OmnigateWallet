package com.omnigate.wallet.data

import com.omnigate.wallet.App
import com.omnigate.wallet.models.Wallet
import io.objectbox.kotlin.boxFor
import io.objectbox.rx.RxQuery
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers.mainThread
import org.jetbrains.anko.AnkoLogger

object WalletsRepository : AnkoLogger {

	fun getWallets(): Single<List<Wallet>> {
		return getWalletsFromServer()
	}

	private fun getWalletsFromServer(): Single<List<Wallet>> {
		return api().requestWallets(AuthManager.apiKey)
				.observeOn(mainThread())
				.doOnSuccess { walletBox().put(it) }
	}

	private fun getWalletsFromDisk(): Single<List<Wallet>> {
		return RxQuery.single(walletBox().query().build())
	}

	private fun api() = Retrofit.omnigateApi

	private fun walletBox() = App.boxStore().boxFor<Wallet>()
}