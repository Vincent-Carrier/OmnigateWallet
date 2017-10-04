package com.omnigate.wallet.data

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.omnigate.wallet.App
import com.omnigate.wallet.defaultSchedulers
import io.reactivex.Single
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationRequest.Builder
import net.openid.appauth.AuthorizationService
import net.openid.appauth.AuthorizationServiceConfiguration
import org.jetbrains.anko.AnkoLogger

object AuthManager : AnkoLogger {

	fun isLoggedIn(): Boolean {
		val apiKey = retrieveApiKey()
		val isLoggedIn = apiKey.isNotEmpty()
		if (isLoggedIn) Retrofit.createOmnigateApi(apiKey)
		return isLoggedIn
	}

	fun startLogin(context: Context) {
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

	fun getApiKeyFromNetwork(accessToken: String): Single<String> {
		return api().requestApiKey(accessToken)
				.defaultSchedulers()
				.map { it.apiKey }
				.doOnSuccess {
					storeApiKey(it)
					Retrofit.createOmnigateApi(it)
				}
	}

	// TODO: Encrypt the API key
	private fun storeApiKey(apiKey: String) {
		App.sharedPrefs().edit().putString("apikey", apiKey).apply()
	}

	private fun retrieveApiKey(): String {
		return App.sharedPrefs().getString("apikey", "")
	}

	private fun api() = Retrofit.authApi
}

