package com.example.retfofitrepositorydemo.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.retfofitrepositorydemo.repository.QuotesRepository

class MainViewModelFactory(private val repository: QuotesRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return MainViewModel(repository) as T
    }
}