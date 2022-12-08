package com.glekhub.nau_web_music.network

import com.glekhub.nau_web_music.models.Question
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MusicApi {

    @GET("api.php")
    suspend fun getQuestion(
        @Query("amount") amount: Int,
        @Query("category") category: Int,
        @Query("difficulty") difficulty: String,
        @Query("type") type: String,
    ): Response<Question>
}