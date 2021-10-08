package com.example.retfofitrepositorydemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.retfofitrepositorydemo.repository.Response
import com.example.retfofitrepositorydemo.viewmodels.MainViewModel
import com.example.retfofitrepositorydemo.viewmodels.MainViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repository = (application as QuoteApplication).quotesRepository

        viewModel =
            ViewModelProvider(this, MainViewModelFactory(repository))[MainViewModel::class.java]

        viewModel.quotes.observe(this, {
            when (it) {
                is Response.Loading -> {

                }
                is Response.Success -> {
                    it.data?.let {
                        Toast.makeText(this, it.results.size.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
                is Response.Error -> {
                    Toast.makeText(this, "Some error occurred", Toast.LENGTH_SHORT).show();
                }
            }
        })
    }
}