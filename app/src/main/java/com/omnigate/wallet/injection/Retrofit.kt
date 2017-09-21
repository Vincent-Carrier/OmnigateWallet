package com.omnigate.wallet.injection

import android.annotation.SuppressLint
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.omnigate.wallet.data.AuthManager
import com.omnigate.wallet.data.OmnigateApi
import com.omnigate.wallet.screens.redirect.RedirectActivity
import dagger.Component
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

@Module
class NetModule(private val baseUrl: String = "https://dev.omnigate.com/api/v1/") {

	@Provides
	@Singleton
	fun provideOmnigateApi(retrofit: Retrofit): OmnigateApi {
		return retrofit.create<OmnigateApi>(OmnigateApi::class.java)
	}

	@Provides
	@Singleton
	fun provideRetrofit(client: OkHttpClient): Retrofit {
		return Retrofit.Builder()
				.client(client)
				.baseUrl(baseUrl)
				.addConverterFactory(GsonConverterFactory.create())
				.addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
				.build()
	}

	@Provides
	@Singleton
	fun provideOkHttpClient(): OkHttpClient {
		// Create a trust manager that does not validate certificate chains
		val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
			override fun getAcceptedIssuers(): Array<X509Certificate> {
				return arrayOf<X509Certificate>()
			}

			@SuppressLint("TrustAllX509TrustManager")
			override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {}

			@SuppressLint("TrustAllX509TrustManager")
			override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {}
		})

// Install the all-trusting trust manager
		val sslContext = SSLContext.getInstance("SSL")
		sslContext.init(null, trustAllCerts, SecureRandom())
		return OkHttpClient.Builder()
				.sslSocketFactory(sslContext.socketFactory, trustAllCerts[0] as X509TrustManager)
				.hostnameVerifier { _, _ -> true }
/*				.addInterceptor({
					val request = it.request().newBuilder().addHeader("api_key", API_KEY).build()
					it.proceed(request)
				})*/.build()
	}
}

@Singleton
@Component(modules = arrayOf(NetModule::class))
interface NetComponent {
	/* Dagger does not accept abstract generics types i.e. Repository<T> */
	fun inject(manager: AuthManager)
	fun inject(activity: RedirectActivity)
}
