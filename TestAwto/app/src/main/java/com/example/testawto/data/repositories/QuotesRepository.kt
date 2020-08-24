package com.example.testawto.data.repositories

import com.example.testawto.data.network.QuotesApi


class QuotesRepository(
    private val api: QuotesApi
) : SafeApiRequest() {

    suspend fun getQuotes() = apiRequest { api.getQuotes() }

}