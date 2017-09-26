package com.omnigate.wallet.screens.redirect

import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.omnigate.wallet.R
import com.omnigate.wallet.data.AuthManager
import com.omnigate.wallet.screens.wallet.WalletActivity
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.startActivity

class RedirectActivity : AppCompatActivity(), AnkoLogger {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_redirect)

		/* getQueryParameter doesn't work properly with #, so we have to replace it */
		AuthManager.accessToken = "Bearer " + Uri.parse(intent.data.toString().replace("#", "?")).getQueryParameter("access_token")

		AuthManager.requestApiKey()

		startActivity<WalletActivity>()
	}
}
