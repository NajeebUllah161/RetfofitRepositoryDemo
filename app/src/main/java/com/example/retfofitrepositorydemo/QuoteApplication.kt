package com.example.retfofitrepositorydemo

import android.app.Application
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.retfofitrepositorydemo.api.QuoteService
import com.example.retfofitrepositorydemo.api.RetrofitHelper
import com.example.retfofitrepositorydemo.db.QuoteDatabase
import com.example.retfofitrepositorydemo.repository.QuotesRepository
import com.example.retfofitrepositorydemo.worker.QuoteWorker
import java.util.concurrent.TimeUnit

class QuoteApplication : Application() {

    lateinit var quotesRepository: QuotesRepository

    override fun onCreate() {
        super.onCreate()
        initialize()
        setupWorker()
    }

    private fun setupWorker() {
        val constraint = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        val workerRequest =
            PeriodicWorkRequest.Builder(QuoteWorker::class.java, 30, TimeUnit.MINUTES)
                .setConstraints(constraint).build()
        WorkManager.getInstance(this).enqueue(workerRequest)

    }

    private fun initialize() {

        val quoteService = RetrofitHelper.getInstance().create(QuoteService::class.java)
        val database = QuoteDatabase.getDatabase(applicationContext)
        quotesRepository = QuotesRepository(quoteService, database, applicationContext)

    }
}