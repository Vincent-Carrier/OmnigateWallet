package com.omnigate.wallet.screens.wallet

import android.annotation.SuppressLint
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.EpoxyModelClass
import com.omnigate.wallet.R
import com.omnigate.wallet.data.AuthManager
import com.trello.rxlifecycle2.android.lifecycle.kotlin.bindToLifecycle
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_wallet.*
import kotlinx.android.synthetic.main.balance_list_item.view.*
import org.jetbrains.anko.AnkoLogger

class WalletActivity : AppCompatActivity(), AnkoLogger {

	private val vm by lazy { ViewModelProviders.of(this).get(WalletViewModel::class.java) }

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_wallet)
		balancesRecyclerView.adapter = vm.adapter()
	}

	override fun onStart() {
		super.onStart()
		if (AuthManager.isLoggedIn())
			vm.requestWallet()
				.bindToLifecycle(this)
				.subscribeBy()
		else AuthManager.startLogin(this)

	}

	override fun onCreateOptionsMenu(menu: Menu): Boolean {
		menuInflater.inflate(R.menu.main, menu)
//		onPrepareOptionsMenu(menu)
		return true
	}
}

@EpoxyModelClass(layout = R.layout.balance_list_item)
abstract class BalanceViewModel() : EpoxyModel<ConstraintLayout>() {

	@EpoxyAttribute var available = ""
	@EpoxyAttribute var currencyCode = ""

	@SuppressLint("SetTextI18n")
	override fun bind(view: ConstraintLayout) {
		with(view) {
			balanceTextView.text = "$available ${currencyCode.substringBefore(".")}"
		}
	}
}