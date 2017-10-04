package com.omnigate.wallet.screens

import android.content.Context
import com.omnigate.wallet.data.AuthManager

internal fun loginCheck(context: Context, func: () -> Any) {
	if (AuthManager.isLoggedIn()) func.invoke()
	else AuthManager.startLogin(context)
}
