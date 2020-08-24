package com.example.testawto.data.models

data class Quote(
    val id: String,
    var sr: String,
    var en: String,
    val author: String,
    val numberOfVotes: Int,
    val rating: Float,
    val addedBy: String
)
