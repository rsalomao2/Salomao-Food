package com.salomao.domain.builder

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.salomao.BuildConfig.ZOMATO_API_KEY
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://developers.zomato.com/"

internal fun createOkHttpClient(): OkHttpClient {
    val logging = HttpLoggingInterceptor()
    logging.level = HttpLoggingInterceptor.Level.BODY
    val okHttpClient = OkHttpClient.Builder()
    okHttpClient.connectTimeout(60, TimeUnit.SECONDS)
    okHttpClient.readTimeout(60, TimeUnit.SECONDS)
    okHttpClient.addInterceptor(logging)
    okHttpClient.addInterceptor(Interceptor {
        val original = it.request()
        val request = original.newBuilder()
            .header("Accept", "application/json")
            .header("user-key", ZOMATO_API_KEY)
            .method(original.method(), original.body())
            .build()
        try {
            it.proceed(request)
        } catch (e: NoSuchElementException) {
            Response.Builder()
                .protocol(Protocol.HTTP_1_1)
                .code(500).build()
        }
    })
    return okHttpClient.build()
}

internal inline fun <reified T> createRetrofit(okHttpClient: OkHttpClient): T {
    val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()

    return retrofit.create<T>(T::class.java)
}
