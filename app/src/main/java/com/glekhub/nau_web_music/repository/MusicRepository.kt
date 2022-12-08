package com.glekhub.nau_web_music.repository

import android.util.Log
import com.glekhub.nau_web_music.network.RetrofitInstance
import com.glekhub.nau_web_music.models.Result

class MusicRepository {

    suspend fun getQuestion(
        amount: Int,
        category: Int,
        difficulty: String,
        type: String
    ): List<Result>? {
        runCatching {
            RetrofitInstance.api.getQuestion(amount, category, difficulty, type)
        }.onSuccess {
            Log.d("GG", "Success: ${it.body()}")
            return it.body()!!.results
        }.onFailure {
            Log.d("GG", "Failure: ${it.message.toString()}")
            return null
        }
        return null
    }
}