package com.omnigate.wallet.data

import com.omnigate.wallet.OmnigateApp
import javax.inject.Inject

/* These classes are responsible for abstracting access to different data sources i.e.
* cache, network and disk storage */

class AuthManager {
	@Inject lateinit var api: OmnigateApi
	init { OmnigateApp.netComponent.inject(this)	}
}