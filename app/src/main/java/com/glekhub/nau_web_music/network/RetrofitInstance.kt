package com.glekhub.nau_web_music.network

import com.glekhub.nau_web_music.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val retrofit by lazy {
        Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    val api: MusicApi by lazy {
        retrofit.create(MusicApi::class.java)
    }
}