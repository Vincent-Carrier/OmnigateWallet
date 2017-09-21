package com.omnigate.wallet.screens.redirect

import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.omnigate.wallet.OmnigateApp
import com.omnigate.wallet.R
import com.omnigate.wallet.data.OmnigateApi
import io.reactivex.android.schedulers.AndroidSchedulers.mainThread
import io.reactivex.rxkotlin.subscribeBy
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.warn
import javax.inject.Inject

class RedirectActivity : AppCompatActivity(), AnkoLogger {

	@Inject lateinit var api: OmnigateApi

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_redirect)

		OmnigateApp.netComponent.inject(this)

		/* getQueryParameter doesn't work properly with #, so we have to replace it */
		val accessToken = "Bearer " + Uri.parse(intent.data.toString().replace("#","?"))
				.getQueryParameter("access_token")
		info(accessToken)

		api.requestWallet(accessToken)
				.observeOn(mainThread())
				.subscribeBy(
						onSuccess = { info("success: $it") },
						onError = { warn("error: $it") }
				)
	}
}
