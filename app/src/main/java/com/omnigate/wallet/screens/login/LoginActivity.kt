package com.omnigate.wallet.screens.login

import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.omnigate.wallet.R
import net.openid.appauth.AuthorizationServiceConfiguration
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import kotlin.properties.Delegates



class LoginActivity : AppCompatActivity(), AnkoLogger {

	var accessToken: String by Delegates.observable("") {
		_, old, new -> debug("$old -> $new")
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_login)
		val LOGIN_URL = "https://dev.omnigate.com/auth/authorize" +
				"?response_type=code&client_id=00000000&redirect_uri=about:blank"

		val serviceConfiguration = AuthorizationServiceConfiguration(
				Uri.parse("https://accounts.google.com/o/oauth2/v2/auth"),
				Uri.parse("https://www.googleapis.com/oauth2/v4/token")
		)

	}
}
