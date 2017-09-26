package com.omnigate.wallet.data

import com.omnigate.wallet.models.ApiKeyRequest
import com.omnigate.wallet.models.ApiKeyResponse
import com.omnigate.wallet.models.Wallet
import io.reactivex.Single
import retrofit2.http.*

interface IOmnigateApi {

	@Headers("Content-Type: application/json")
	@POST("profile/apikey")
	fun requestApiKey(@Header("Authorization") accessToken: String,
	                  @Body request: ApiKeyRequest): Single<ApiKeyResponse>

	@GET("wallet")
	fun requestWallet(@Header("X-APIKey") apiKey: String) : Single<Wallet>
}