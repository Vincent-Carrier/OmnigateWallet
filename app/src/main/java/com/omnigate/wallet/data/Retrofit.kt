package com.omnigate.wallet.data

import android.annotation.SuppressLint
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

internal object Retrofit {

	internal val authApi = authApi(retrofit(okHttpClient()))

	internal lateinit var omnigateApi: OmnigateApi

	internal fun createOmnigateApi(apiKey: String) {
		omnigateApi = omnigateApi(retrofit(okHttpClient(apiKey)))
	}

	private fun authApi(retrofit: Retrofit): AuthApi {
		return retrofit.create<AuthApi>(AuthApi::class.java)
	}

	private fun omnigateApi(retrofit: Retrofit): OmnigateApi {
		return retrofit.create<OmnigateApi>(OmnigateApi::class.java)
	}

	private fun retrofit(client: OkHttpClient): Retrofit {
		return Retrofit.Builder()
				.client(client)
				.baseUrl("https://dev.omnigate.com/api/v1/")
				.addConverterFactory(GsonConverterFactory.create())
				.addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
				.build()
	}

	private fun okHttpClient(apiKey: String = ""): OkHttpClient {
		// Create a trust manager that does not validate certificate chains
		val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
			override fun getAcceptedIssuers() = arrayOf<X509Certificate>()

			@SuppressLint("TrustAllX509TrustManager")
			override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {}

			@SuppressLint("TrustAllX509TrustManager")
			override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {}
		})

// Install the all-trusting trust manager
		val sslContext = SSLContext.getInstance("SSL")
		sslContext.init(null, trustAllCerts, SecureRandom())
		val builder = OkHttpClient.Builder()
				.sslSocketFactory(sslContext.socketFactory, trustAllCerts[0] as X509TrustManager)
				.hostnameVerifier { _, _ -> true }
				.addInterceptor(HttpLoggingInterceptor().apply { level = BODY })

		if (apiKey.isNotEmpty())
				builder.addInterceptor({
					val request = it.request().newBuilder().addHeader("X-APIKey", apiKey).build()
					it.proceed(request)
				})
		return builder.build()
	}
}