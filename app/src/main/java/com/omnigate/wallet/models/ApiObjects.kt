package com.omnigate.wallet.models

import com.google.gson.annotations.SerializedName


data class ApiKeyRequest(@SerializedName("description") val description: String,
                         @SerializedName("roles") val roles: List<String>)


data class ApiKeyResponse(
		@SerializedName("apikey") val apiKey: String
)