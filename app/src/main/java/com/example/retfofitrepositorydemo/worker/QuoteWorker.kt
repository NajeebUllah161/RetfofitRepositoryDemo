package com.example.retfofitrepositorydemo.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.retfofitrepositorydemo.QuoteApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class QuoteWorker(private val context: Context, params: WorkerParameters) :
    Worker(context, params) {
    override fun doWork(): Result {
        Log.d("doWork", "WorkerCalled")
        val repository = (context as QuoteApplication).quotesRepository
        CoroutineScope(Dispatchers.IO).launch {
            repository.getQuotesBackground()
        }

        return Result.success()
    }
}