package com.crypto.tracker.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.crypto.tracker.model.CoinPrices
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

/**
 * ViewModel for Home screen.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Empty)
    val uiState: StateFlow<HomeUiState> = _uiState

}

sealed class HomeUiState {
    object Empty : HomeUiState()
    class Loading(val forceRefresh: Boolean) : HomeUiState()
    class Loaded(val data: CoinPrices) : HomeUiState()
    class Error(val message: String) : HomeUiState()
}