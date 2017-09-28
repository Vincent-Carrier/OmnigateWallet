package com.omnigate.wallet.data

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.omnigate.wallet.App
import com.omnigate.wallet.models.ApiKeyRequest
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationRequest.Builder
import net.openid.appauth.AuthorizationService
import net.openid.appauth.AuthorizationServiceConfiguration
import org.jetbrains.anko.AnkoLogger

object AuthManager : AnkoLogger {
	var accessToken = ""

	var apiKey = ""

	fun isLoggedIn() = apiKey.isNotEmpty()

	/* If the key isn't stored on disk, get it from the network and store it */
	fun fetchApiKey() {
		val key = retrieveApiKey()
		apiKey = if (key.isNotEmpty()) key else api().requestApiKey(
				accessToken, ApiKeyRequest("Development", listOf("profile-admin")))
				.retry(3)
				.blockingGet().apiKey // blockingGet usually isn't best practice, but it works well here
		storeApiKey(apiKey)
	}

	// TODO: Encrypt the API key
	private fun storeApiKey(apiKey: String) {
		App.sharedPrefs().edit().putString("apikey", apiKey).apply()
	}

	private fun retrieveApiKey(): String {
		return App.sharedPrefs().getString("apikey", "")
	}

	private fun api() = OmnigateApi.api

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
}

