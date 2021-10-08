package com.example.retfofitrepositorydemo.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retfofitrepositorydemo.models.QuoteList
import com.example.retfofitrepositorydemo.repository.QuotesRepository
import com.example.retfofitrepositorydemo.repository.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: QuotesRepository) : ViewModel() {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getQuotes(1)
        }
    }

    val quotes: LiveData<Response<QuoteList>>
        get() = repository.quotes

}