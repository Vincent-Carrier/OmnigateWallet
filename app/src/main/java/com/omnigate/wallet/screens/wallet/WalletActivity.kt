package com.omnigate.wallet.screens.wallet

import android.app.PendingIntent
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.omnigate.wallet.R
import net.openid.appauth.AuthorizationRequest.Builder
import net.openid.appauth.AuthorizationRequest.RESPONSE_TYPE_TOKEN
import net.openid.appauth.AuthorizationService
import net.openid.appauth.AuthorizationServiceConfiguration
import org.jetbrains.anko.AnkoLogger



class WalletActivity : AppCompatActivity(), AnkoLogger {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_wallet)
		startLogin()
	}

	private fun startLogin() {
		val config = AuthorizationServiceConfiguration(
				Uri.parse("https://dev.omnigate.com/auth/authorize"),
				Uri.parse("https://dev.omnigate.com/auth/token")
		)
		val clientId = "31630427"
		val redirectUri = Uri.parse("com.omnigate.wallet:/oauth2callback")
		val request = Builder(config, clientId, RESPONSE_TYPE_TOKEN, redirectUri).build()

		val postAuthIntent = Intent("com.omnigate.wallet.action.POST_AUTH")
		val pendingIntent = PendingIntent.getActivity(this, request.hashCode(),
				postAuthIntent, 0)
		AuthorizationService(this).performAuthorizationRequest(request, pendingIntent)
	}
}