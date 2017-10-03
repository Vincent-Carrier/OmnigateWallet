package com.omnigate.wallet.screens.wallet

import android.arch.lifecycle.ViewModel
import com.airbnb.epoxy.TypedEpoxyController
import com.omnigate.wallet.data.WalletRepository
import com.omnigate.wallet.models.Balance
import com.omnigate.wallet.models.Wallet
import io.reactivex.Single
import org.jetbrains.anko.AnkoLogger

class WalletViewModel : ViewModel(), AnkoLogger {


	private val controller = BalancesController()

	internal fun adapter() = controller.adapter

	internal fun requestWallet(): Single<Wallet> {
		return WalletRepository.requestWallet()
				.doOnSuccess {
					controller.setData(it.balances)
				}
	}

	class BalancesController() : TypedEpoxyController<List<Balance>>() {
		override fun buildModels(balances: List<Balance>) {
			balances.forEach {
				balanceView {
					id(it.currencyCode)
					available(it.available)
					currencyCode(it.currencyCode)
				}
			}
		}
	}
}