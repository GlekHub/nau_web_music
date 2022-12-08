package com.glekhub.nau_web_music.models

data class Question(
    val response_code: Int,
    val results: List<Result>
)