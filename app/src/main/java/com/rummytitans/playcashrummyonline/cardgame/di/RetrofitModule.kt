package com.rummytitans.playcashrummyonline.cardgame.di

import android.os.Build
import android.text.TextUtils
import com.google.gson.Gson
import com.rummytitans.playcashrummyonline.cardgame.BuildConfig
import com.rummytitans.playcashrummyonline.cardgame.MainApplication
import com.rummytitans.playcashrummyonline.cardgame.api.APIInterface
import com.rummytitans.playcashrummyonline.cardgame.data.SharedPreferenceStorage
import com.rummytitans.playcashrummyonline.cardgame.utils.MyConstants
import com.rummytitans.sdk.cardgame.di.anotation.Rummy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

    @Singleton
    @Provides
    fun getApiInterface(@Rummy retrofit: Retrofit) = retrofit.create(APIInterface::class.java)

    @Singleton
    @Provides
    @Rummy
    fun provideGson() = Gson()

    @Singleton
    @Provides
    @Rummy
    fun getRetrofit(@Rummy okHttpClient: OkHttpClient):Retrofit {
        val baseurl = MainApplication.appUrl
        val baseApiUrl= if (TextUtils.isEmpty(baseurl))
          MyConstants.APP_CURRENT_URL
        else
          baseurl

        return Retrofit.Builder()
            .baseUrl(baseApiUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient).build()
    }

    @Singleton
    @Provides
    @Rummy
    fun getOkHttpClient(@Rummy interceptor: Interceptor) =
        OkHttpClient.Builder().addInterceptor(interceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS).build()

    @Singleton
    @Provides
    @Rummy
    fun getInterceptor(pref: SharedPreferenceStorage): Interceptor {
        return Interceptor { chain: Interceptor.Chain ->
            val request = chain.request()
            val httpUrl = request.url
            val url = httpUrl.newBuilder().build()
            val builder = request.newBuilder().url(url)
            builder.addHeader("AppVersion", BuildConfig.VERSION_CODE.toString())
            builder.addHeader("AppType", "${MyConstants.APP_TYPE}") //uses in BaseViewModel and Analytic helper fireevent()
            builder.addHeader("GameType", "1")
            builder.addHeader("DeviceName", Build.MODEL)
            builder.addHeader("DeviceOS", "Android")
            builder.addHeader("IsPlayStore",BuildConfig.installFrom.toString())
//            pref.toUserDetail(gson)?.let {
//                builder.addHeader("UserId", it.UserId.toString())
//                builder.addHeader("AuthExpire", it.AuthExpire)
//                builder.addHeader("ExpireToken", it.ExpireToken)
//            }
            val requestBuilder = builder.build()
            return@Interceptor chain.proceed(requestBuilder)
        }
    }

}
