package com.example.data.remote

import com.example.data.BuildConfig
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ServiceFactory {

    companion object {

        fun <T> createService(clazz: Class<T>, endPoint: String): T {
            val httpClientBuilder = OkHttpClient.Builder()
            if (BuildConfig.DEBUG) {
                val logger = HttpLoggingInterceptor()
                logger.level = HttpLoggingInterceptor.Level.BODY
                httpClientBuilder.addInterceptor(logger)
            }
            val retrofit = Retrofit.Builder()
                    .baseUrl(endPoint)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(MoshiConverterFactory.create())
                    .client(httpClientBuilder.build())
                    .build()
            val service = retrofit.create(clazz)
            return service
        }
    }

}