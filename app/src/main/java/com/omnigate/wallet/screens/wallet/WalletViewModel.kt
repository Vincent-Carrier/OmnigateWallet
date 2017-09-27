package com.omnigate.wallet.screens.wallet

import android.arch.lifecycle.ViewModel
import com.airbnb.epoxy.TypedEpoxyController
import com.omnigate.wallet.data.WalletManager
import com.omnigate.wallet.models.Balance
import com.omnigate.wallet.models.Wallet
import io.reactivex.Single
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class WalletViewModel : ViewModel(), AnkoLogger {


	private val controller = BalancesController()

	internal fun adapter() = controller.adapter

	internal fun requestWallet(): Single<Wallet> {
		return WalletManager.requestWallet()
				.doOnSuccess {
					controller.setData(it.balances)
					controller.requestModelBuild()
					info(it.balances)
				}
	}

	class BalancesController() : TypedEpoxyController<List<Balance>>() {
		override fun buildModels(balances: List<Balance>) {
			for (balance in balances) {
				BalanceModel_(balance).id(balance.currencyCode).addTo(this)
			}
		}
	}
}