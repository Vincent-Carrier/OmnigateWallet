package com.omnigate.wallet.screens.wallet

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.omnigate.wallet.R
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationResponse
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import kotlin.properties.Delegates



class WalletActivity : AppCompatActivity(), AnkoLogger {

	private var accessToken: String by Delegates.observable("") {
		_, old, new -> debug("$old -> $new")
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_wallet)

		receiveAccessToken()
	}

	private fun receiveAccessToken() {
		val response = AuthorizationResponse.fromIntent(intent)
		val exception = AuthorizationException.fromIntent(intent)

		if (response != null) { accessToken = response.accessToken!! }
		else { TODO() }
	}
}
