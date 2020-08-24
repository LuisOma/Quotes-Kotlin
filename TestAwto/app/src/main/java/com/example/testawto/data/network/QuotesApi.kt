package com.example.testawto.data.network

import com.example.testawto.data.models.Quote
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface QuotesApi {
    @GET("quotes")
    suspend fun getQuotes() : Response<List<Quote>>

    companion object{
        operator fun invoke() : QuotesApi {
            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://programming-quotes-api.herokuapp.com/")
                .build()
                .create(QuotesApi::class.java)
        }
    }
}
