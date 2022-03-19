package com.example.assestment02.rest

import android.media.MediaPlayer
import androidx.viewbinding.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object SongService {

    private val httpLoggingInterceptor by lazy{
        HttpLoggingInterceptor().apply {
            if (BuildConfig.DEBUG){
                level = HttpLoggingInterceptor.Level.BODY
            }
        }
    }

    private val okHttpClient by lazy {
       OkHttpClient.Builder()
           .addInterceptor(SongInterceptor())
           .addInterceptor(httpLoggingInterceptor)
           .connectTimeout(30, TimeUnit.SECONDS)
           .readTimeout(30, TimeUnit.SECONDS)
           .writeTimeout(30, TimeUnit.SECONDS)
           .build()
    }

    val retrofitService =
        Retrofit.Builder()
            .baseUrl(APISongInterface.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
            .create(APISongInterface::class.java)


    var mediaPlayer: MediaPlayer? = null
}