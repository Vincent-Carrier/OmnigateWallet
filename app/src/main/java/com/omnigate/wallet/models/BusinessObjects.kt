package com.omnigate.wallet.models

import com.google.gson.annotations.SerializedName
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.Index

@Entity
data class Wallet(
		@Id var id: Long = 0,
		@SerializedName("default") val default: Boolean,
		@SerializedName("walletname") val walletName: String,
		@SerializedName("waid") val walletId: String,
		@SerializedName("balances") val balances: List<Balance>
)

@Entity
data class Balance(
		@Id var id: Long = 0,
		@SerializedName("currencycode") @Index val currencyCode: String,
		@SerializedName("currencyname") val currencyName: String,
		@SerializedName("available") val available: String,
		@SerializedName("held") val held: String,
		@SerializedName("total") val total: String
)