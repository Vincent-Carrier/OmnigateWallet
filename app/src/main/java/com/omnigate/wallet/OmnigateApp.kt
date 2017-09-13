package com.omnigate.wallet

import android.app.Application
import com.omnigate.wallet.injection.DaggerNetComponent
import com.omnigate.wallet.injection.NetComponent

class OmnigateApp : Application() {

	companion object {
		lateinit var netComponent: NetComponent
	}

	override fun onCreate() {
		super.onCreate()
		netComponent = DaggerNetComponent.create()
	}
}

