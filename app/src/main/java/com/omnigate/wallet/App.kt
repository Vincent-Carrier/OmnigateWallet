package com.omnigate.wallet

import android.app.Application
import android.content.SharedPreferences
import com.omnigate.wallet.models.MyObjectBox
import io.objectbox.BoxStore
import io.reactivex.plugins.RxJavaPlugins
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.warn

class App : Application(), AnkoLogger {

	companion object {
		private var app: App? = null
		val PREFS = "prefs"
		fun sharedPrefs(): SharedPreferences = app!!.getSharedPreferences(PREFS, MODE_PRIVATE)
		fun boxStore() = (app!! as App).boxStore
	}

	init {
		app = this
	}

	val boxStore: BoxStore by lazy { MyObjectBox.builder().androidContext(this).build() }

	override fun onCreate() {
		super.onCreate()
		RxJavaPlugins.setErrorHandler { warn(it.message, it) }
	}
}

