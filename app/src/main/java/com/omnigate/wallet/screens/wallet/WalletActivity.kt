package com.omnigate.wallet.screens.wallet

import android.annotation.SuppressLint
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AppCompatActivity
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.EpoxyModelClass
import com.omnigate.wallet.R
import com.omnigate.wallet.data.AuthManager
import com.omnigate.wallet.models.Balance
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_wallet.*
import kotlinx.android.synthetic.main.balance_list_item.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.warn

class WalletActivity : AppCompatActivity(), AnkoLogger {

	private val vm by lazy { ViewModelProviders.of(this).get(WalletViewModel::class.java) }

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_wallet)
		balancesRecyclerView.adapter = vm.adapter()
	}

	override fun onStart() {
		super.onStart()
		AuthManager.startLoginIfNeeded(this)
		vm.requestWallet()
				.subscribeBy(
						onSuccess = { info("requestWallet(): $it") },
						onError = { warn(it.localizedMessage) }
				)
	}
}

@EpoxyModelClass(layout = R.layout.balance_list_item)
abstract class BalanceModel(balance: Balance) : EpoxyModel<ConstraintLayout>() {

	@EpoxyAttribute var available: String = balance.available
	@EpoxyAttribute var currencyCode: String = balance.currencyCode

	@SuppressLint("SetTextI18n")
	override fun bind(view: ConstraintLayout) {
		with(view) {
			balanceTextView.text = available + currencyCode
		}
	}
}