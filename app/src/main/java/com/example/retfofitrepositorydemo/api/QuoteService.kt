package com.example.retfofitrepositorydemo.api

import com.example.retfofitrepositorydemo.models.QuoteList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface QuoteService {

    @GET("/quotes")
    suspend fun getQuotes(@Query("page") page: Int): Response<QuoteList>
}