package com.omnigate.wallet.models

import com.google.gson.annotations.SerializedName


data class User(@SerializedName("email_address") val emailAddress: String)