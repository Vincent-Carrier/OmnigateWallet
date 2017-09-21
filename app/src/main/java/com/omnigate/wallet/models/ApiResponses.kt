package com.omnigate.wallet.models

import com.google.gson.annotations.SerializedName

data class Wallet(
		@SerializedName("waid") val walletId: String,
		@SerializedName("balances") val balances: List<Balance>
)

data class Balance(
		@SerializedName("currencycode") val currencyCode: String,
		@SerializedName("currencyname") val currencyName: String,
		@SerializedName("available") val available: String,
		@SerializedName("held") val held: String,
		@SerializedName("total") val total: String
)