package com.omnigate.wallet.data

import com.omnigate.wallet.models.Wallet
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header


interface OmnigateApi {

	@GET("wallet")
	fun requestWallet(@Header("Authorization") accessToken: String) : Single<Wallet>
}