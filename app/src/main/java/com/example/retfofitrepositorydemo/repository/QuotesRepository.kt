package com.example.retfofitrepositorydemo.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.retfofitrepositorydemo.api.QuoteService
import com.example.retfofitrepositorydemo.db.QuoteDatabase
import com.example.retfofitrepositorydemo.models.QuoteList
import com.example.retfofitrepositorydemo.utils.NetworkUtils

class QuotesRepository(
    private val quoteService: QuoteService,
    private val quoteDatabase: QuoteDatabase,
    private val applicationContext: Context
) {

    private val quotesLiveData = MutableLiveData<Response<QuoteList>>()

    val quotes: LiveData<Response<QuoteList>>
        get() = quotesLiveData

    suspend fun getQuotes(page: Int) {

        if (NetworkUtils.isInternetAvailable(context = applicationContext)) {
            try {
                val result = quoteService.getQuotes(page)
                if (result.body() != null) {
                    quoteDatabase.getDao().addQuotes(result.body()!!.results)
                    quotesLiveData.postValue(Response.Success(result.body()))
                }
            } catch (e: Exception) {
                quotesLiveData.postValue(Response.Error(e.message.toString()))

            }

        } else {
            val quotes = quoteDatabase.getDao().getQuotes()
            val quoteList = QuoteList(1, 1, 1, quotes, 1, 1)
            quotesLiveData.postValue(Response.Success(quoteList))
        }
    }

    suspend fun getQuotesBackground() {
        val randomNumber = (Math.random() * 10).toInt()
        val result = quoteService.getQuotes(randomNumber)
        if (result?.body() != null) {
            quoteDatabase.getDao().addQuotes(result.body()!!.results)
        }
    }
}