package com.ebt.rickandmortycardswipe.presentation.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.ebt.rickandmortycardswipe.data.util.Result
import com.ebt.rickandmortycardswipe.databinding.ActivityMainBinding
import com.ebt.rickandmortycardswipe.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initObservers()
        viewModel.getCharacters(1)
    }

    private fun initObservers() {
        viewModel.characters.observe(this, Observer {
            when (it) {
                is Result.Success -> {
                    hideLoading()
                    Log.d("AppTAG", "Count: ${it.data?.characters?.size}")
                }
                is Result.Error -> {
                    hideLoading()
                }
                is Result.Loading -> {
                    showLoading()
                }
            }
        })
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.progressBar.visibility = View.INVISIBLE
    }
}