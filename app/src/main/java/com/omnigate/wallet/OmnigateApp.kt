package com.omnigate.wallet

import android.app.Application
import io.reactivex.plugins.RxJavaPlugins
import io.realm.Realm
import io.realm.RealmConfiguration
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.warn

class OmnigateApp : Application(), AnkoLogger {
	override fun onCreate() {
		super.onCreate()
		Realm.init(this)
		Realm.setDefaultConfiguration(
				RealmConfiguration.Builder()
						.build()
		)
		RxJavaPlugins.setErrorHandler { warn(it.message, it) }
	}
}

