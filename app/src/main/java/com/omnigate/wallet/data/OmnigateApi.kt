package com.omnigate.wallet.data

import retrofit2.http.POST
import retrofit2.http.Query


interface OmnigateApi {

	@POST("auth/token")
	fun requestAuthToken(@Query("grant_type") grantType: String = "password",
	                     @Query("username") userName: String,
	                     @Query("password") password: String)
}