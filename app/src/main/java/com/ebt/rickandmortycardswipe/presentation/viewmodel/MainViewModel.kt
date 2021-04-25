package com.ebt.rickandmortycardswipe.presentation.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ebt.rickandmortycardswipe.data.model.APIResponse
import com.ebt.rickandmortycardswipe.data.util.Result
import com.ebt.rickandmortycardswipe.domain.usecase.GetCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val app: Application,
    private val getCharactersUseCase: GetCharactersUseCase
) : ViewModel() {
    val characters: MutableLiveData<Result<APIResponse>> = MutableLiveData()

    fun getCharacters(page: Int) = viewModelScope.launch(Dispatchers.IO) {
        characters.postValue(Result.Loading())

        try {
            if (isNetworkAvailable(app)) {
                val result = getCharactersUseCase.execute(page)
                characters.postValue(result)
            } else {
                characters.postValue(Result.Error("No internet connection"))
            }
        } catch (e: Exception) {
            characters.postValue(Result.Error(e.message.toString()))
        }
    }

    private fun isNetworkAvailable(context: Context?): Boolean {
        if (context == null) return false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false
    }
}