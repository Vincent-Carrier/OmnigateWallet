package com.omnigate.wallet.screens.wallet

import android.arch.lifecycle.ViewModel
import com.airbnb.epoxy.TypedEpoxyController
import com.omnigate.wallet.data.WalletManager
import com.omnigate.wallet.models.Balance
import com.omnigate.wallet.models.Wallet
import io.reactivex.Single

class WalletViewModel : ViewModel() {


	private val controller = BalancesController()

	internal fun adapter() = controller.adapter

	internal fun requestWallet(): Single<Wallet> {
			return WalletManager.requestWallet()
					.doOnSuccess {
						controller.setData(it.balances)
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