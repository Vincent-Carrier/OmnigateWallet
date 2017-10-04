package com.omnigate.wallet.data

import com.omnigate.wallet.models.ApiKeyRequest
import com.omnigate.wallet.models.ApiKeyResponse
import com.omnigate.wallet.models.Wallet
import com.omnigate.wallet.models.WalletListResponse
import io.reactivex.Single
import retrofit2.http.*

interface AuthApi {
	@Headers("Content-Type: application/json")
	@POST("profile/apikey")
	fun requestApiKey(@Header("Authorization") accessToken: String,
	                  @Body request: ApiKeyRequest =
	                  ApiKeyRequest("Development", listOf("profile-admin"))
	): Single<ApiKeyResponse>
}

interface OmnigateApi {
	@GET("wallet")
	fun requestWallet() : Single<Wallet>

	@GET("wallet/list")
	fun requestWallets() : Single<WalletListResponse>
}