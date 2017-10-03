package com.omnigate.wallet.screens.wallet

import android.arch.lifecycle.ViewModel
import com.airbnb.epoxy.TypedEpoxyController
import com.omnigate.wallet.data.WalletsRepository
import com.omnigate.wallet.models.Balance
import com.omnigate.wallet.models.Wallet
import io.reactivex.Single
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class WalletViewModel : ViewModel(), AnkoLogger {


	private val controller = BalancesController()

	internal fun adapter() = controller.adapter

	internal fun getWallet(): Single<Wallet> {
		return WalletsRepository.getWallets()
				.map { it.first() }
				.doOnSuccess {
					info(it)
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