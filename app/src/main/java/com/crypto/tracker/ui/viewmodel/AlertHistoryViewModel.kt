package com.crypto.tracker.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.crypto.tracker.data.local.AlertDao
import com.crypto.tracker.model.Alert
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * ViewModel for Alert History screen.
 */
@HiltViewModel
class AlertHistoryViewModel @Inject constructor(
    private val alertDao: AlertDao,
    savedStateHandle: SavedStateHandle,
    application: Application
) : AndroidViewModel(application) {

    private val coinId: String = checkNotNull(savedStateHandle["coinId"])
    private val _uiState = MutableStateFlow<AlertHistoryState>(AlertHistoryState.Empty)
    val uiState: StateFlow<AlertHistoryState> = _uiState

    init {
        Timber.d("AlertHistoryViewModel created coinId: $coinId")
        fetchAlertsForCoin(coinId)
    }

    private fun fetchAlertsForCoin(coinId: String) {
        Timber.d("fetchAlertsForCoin coinID: $coinId")

        viewModelScope.launch {
            alertDao.getAllAlertsForCoin(coinId = coinId).collect { result ->
                _uiState.value = if (result.isEmpty()) {
                    AlertHistoryState.Empty
                } else {
                    AlertHistoryState.Loaded(result)
                }
            }
        }
    }
}

sealed class AlertHistoryState {
    object Empty : AlertHistoryState()
    class Loaded(val data: List<Alert>) : AlertHistoryState()
}