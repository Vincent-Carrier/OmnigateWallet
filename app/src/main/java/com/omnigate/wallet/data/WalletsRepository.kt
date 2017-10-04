package com.omnigate.wallet.data

import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import com.omnigate.wallet.App
import com.omnigate.wallet.defaultSchedulers
import com.omnigate.wallet.models.Wallet
import io.objectbox.kotlin.boxFor
import io.objectbox.rx.RxQuery
import io.reactivex.Single
import org.jetbrains.anko.AnkoLogger

object WalletsRepository : AnkoLogger {

	fun getWallets(): Single<List<Wallet>> {
		return ReactiveNetwork.checkInternetConnectivity()
				.defaultSchedulers()
				.flatMap { isConnected ->
					if (isConnected) getWalletsFromServer()
					else getWalletsFromDisk()
				}
	}

	private fun getWalletsFromServer(): Single<List<Wallet>> {
		return api().requestWallets()
				.defaultSchedulers()
				.map { it.walletlist }
				.doOnSuccess {
//					walletBox().put(it)
				}
	}

	private fun getWalletsFromDisk(): Single<List<Wallet>> {
		return RxQuery.single(walletBox().query().build())
				.defaultSchedulers()
	}

	private fun api() = Retrofit.omnigateApi

	private fun walletBox() = App.boxStore().boxFor<Wallet>()
}