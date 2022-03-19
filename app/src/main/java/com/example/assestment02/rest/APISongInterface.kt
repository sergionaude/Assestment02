package com.example.assestment02.rest

import com.example.assestment02.model.songR
import io.reactivex.Single
import retrofit2.http.GET

interface APISongInterface {
    @GET("search?term=rock&amp;media=music&amp;entity=song&amp;limit=50")
    fun getPop(): Single<songR>

    @GET("search?term=classick&amp;media=music&amp;entity=song&amp;limit=50")
    fun getClassic(): Single<songR>

    @GET("search?term=pop&amp;media=music&amp;entity=song&amp;limit=50")
    fun getSong(): Single<songR>

    companion object{
        const val BASE_URL = "https://itunes.apple.com/"
    }
}