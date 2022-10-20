package com.crypto.tracker.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.crypto.tracker.data.remote.PricesRemoteDataSource
import com.crypto.tracker.model.CoinPrices
import com.crypto.tracker.model.Result
import com.crypto.tracker.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * ViewModel for Home screen.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val pricesRemoteDataSource: PricesRemoteDataSource,
    application: Application
) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Empty)
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        Timber.d("created")
        fetchPrices()
    }

    fun refresh() {
        Timber.d("refresh")
        fetchPrices(forceRefresh = true)
    }

    private fun fetchPrices(forceRefresh: Boolean = false) {
        Timber.d("fetchPrices forceRefresh: $forceRefresh")
        viewModelScope.launch {
            pricesRemoteDataSource.fetchPrices(
                coinIds = Constants.COIN_IDS,
                currencies = Constants.CURRENCIES,
                include24hrChange = true
            ).collect { result ->
                Timber.d("result: $result")
                _uiState.value = when (result.status) {
                    Result.Status.LOADING -> {
                        if (uiState.value is HomeUiState.Loaded) {
                            uiState.value
                        } else {
                            HomeUiState.Loading(forceRefresh)
                        }
                    }
                    Result.Status.SUCCESS -> result.data?.let { HomeUiState.Loaded(it) }
                        ?: HomeUiState.Empty
                    Result.Status.ERROR -> HomeUiState.Error(result.message ?: "Error")
                }
            }
        }
    }
}

sealed class HomeUiState {
    object Empty : HomeUiState()
    class Loading(val forceRefresh: Boolean) : HomeUiState()
    class Loaded(val data: CoinPrices) : HomeUiState()
    class Error(val message: String) : HomeUiState()
}