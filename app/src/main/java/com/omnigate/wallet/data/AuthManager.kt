package com.omnigate.wallet.data

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.omnigate.wallet.models.ApiKeyRequest
import io.reactivex.android.schedulers.AndroidSchedulers.mainThread
import io.reactivex.rxkotlin.subscribeBy
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationRequest.Builder
import net.openid.appauth.AuthorizationService
import net.openid.appauth.AuthorizationServiceConfiguration
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import kotlin.properties.Delegates

/* These classes are responsible for abstracting access to different data sources i.e.
* cache, network and disk storage */
object AuthManager : AnkoLogger {
	var accessToken: String by Delegates.observable("") { _, _, newValue ->
		info(newValue)
	}

	var apiKey: String by Delegates.observable("") { _, _, newValue ->
		info(newValue)
	}

	private fun isLoggedIn() = accessToken.isNotEmpty()

	fun requestApiKey() = api().requestApiKey(accessToken, ApiKeyRequest(
			"Development", listOf("profile-admin")))
			.observeOn(mainThread())
			.subscribeBy(
					onSuccess = {
						apiKey = it.apiKey
						info("requestApiKey(): $apiKey")
					},
					onError = { it.printStackTrace() }
			)

	private fun api() = OmnigateApi.api

	fun startLoginIfNeeded(context: Context) {
		if (!AuthManager.isLoggedIn()) {
			val config = AuthorizationServiceConfiguration(
					Uri.parse("https://dev.omnigate.com/auth/authorize"),
					Uri.parse("https://dev.omnigate.com/auth/token")
			)
			val clientId = "31630427"
			val redirectUri = Uri.parse("com.omnigate.wallet:/oauth2callback")
			val request = Builder(config, clientId, AuthorizationRequest.RESPONSE_TYPE_TOKEN, redirectUri).build()

			val postAuthIntent = Intent("com.omnigate.wallet.action.POST_AUTH")
			val pendingIntent = PendingIntent.getActivity(context, request.hashCode(),
					postAuthIntent, 0)
			AuthorizationService(context).performAuthorizationRequest(request, pendingIntent)
		}
	}
}

