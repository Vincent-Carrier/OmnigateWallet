package com.omnigate.wallet.screens.login

import android.app.PendingIntent
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.omnigate.wallet.R
import com.omnigate.wallet.models.CLIENT_ID
import com.omnigate.wallet.screens.wallet.WalletActivity
import net.openid.appauth.AuthorizationRequest.Builder
import net.openid.appauth.AuthorizationRequest.RESPONSE_TYPE_CODE
import net.openid.appauth.AuthorizationService
import net.openid.appauth.AuthorizationServiceConfiguration
import org.jetbrains.anko.AnkoLogger

class LoginActivity : AppCompatActivity(), AnkoLogger {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_login)
		startUserLoginWebview()
	}

	private fun startUserLoginWebview() {
		val config = AuthorizationServiceConfiguration(
				Uri.parse("https://dev.omnigate.com/auth/authorize"),
				Uri.parse("https://dev.omnigate.com/auth/token"))
		val redirectUri = Uri.parse("https://dev.omnigate.com/oauth2redirect")
		val request = Builder(config, CLIENT_ID, RESPONSE_TYPE_CODE, redirectUri)
//				.setScope("profile email")
				//.setLoginHint(emailAddress)
				.build()

		val intent = PendingIntent.getActivity(this, 0,
				Intent(this, WalletActivity::class.java), 0)

		val service = AuthorizationService(this)
		service.performAuthorizationRequest(request, intent)
	}
}
