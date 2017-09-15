package com.omnigate.wallet.injection

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.omnigate.wallet.data.OmnigateApi
import com.omnigate.wallet.data.UserRepository
import dagger.Component
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetModule(private val baseUrl: String = "https://dev.omnigate/api/v1/") {

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
		return OkHttpClient.Builder()
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
	fun inject(repository: UserRepository)
}
