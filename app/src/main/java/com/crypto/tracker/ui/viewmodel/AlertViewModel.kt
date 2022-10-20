package com.crypto.tracker.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.crypto.tracker.CryptoTrackerApplication
import com.crypto.tracker.R
import com.crypto.tracker.data.local.AlertDao
import com.crypto.tracker.data.remote.PricesRemoteDataSource
import com.crypto.tracker.model.Alert
import com.crypto.tracker.model.Coin
import com.crypto.tracker.model.Result
import com.crypto.tracker.util.Constants
import com.crypto.tracker.util.InputUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * ViewModel for Alert screen.
 */
@HiltViewModel
class AlertViewModel @Inject constructor(
    private val pricesRemoteDataSource: PricesRemoteDataSource,
    private val alertDao: AlertDao,
    savedStateHandle: SavedStateHandle,
    application: Application
) : AndroidViewModel(application), UserInputCallbacks {

    private val coinId: String = checkNotNull(savedStateHandle["coinId"])
    private val _priceUiState = MutableStateFlow<AlertPriceUiState>(AlertPriceUiState.Empty)
    private val _inputUiState = MutableStateFlow<AlertInputUiState>(AlertInputUiState.Normal())
    private val _minText = MutableStateFlow("")
    private val _maxText = MutableStateFlow("")

    val priceUiState: StateFlow<AlertPriceUiState> = _priceUiState
    val inputUiState: StateFlow<AlertInputUiState> = _inputUiState

    init {
        Timber.d("created coinId: $coinId")
        fetchPrice(coinId)
    }

    override fun onCheckAlert(onResult: (String) -> Unit) {
        Timber.d("onCheckAlert min: ${_minText.value} max: ${_maxText.value}")

        val min = _minText.value
        val max = _maxText.value
        val minValid = InputUtils.isMinInputValid(min, max)
        val maxValid = InputUtils.isMaxInputValid(max, min)
        if (!minValid || !maxValid) {
            _inputUiState.value = AlertInputUiState.Error(
                _minText.value,
                _maxText.value,
                minMaxError = getApplication<CryptoTrackerApplication>().getString(R.string.min_max_error)
            )
            return
        }

        val temporaryAlert = Alert(min = min.toDouble(), max = max.toDouble())
        fetchPrice(coinId, temporaryAlert = temporaryAlert, onResult = onResult)
    }

    override fun onValidateMinInput(input: String) {
        Timber.d("onValidateMinInput input: $input")
        _minText.value = InputUtils.validatePriceInput(input, _minText.value)
        _inputUiState.value = AlertInputUiState.Normal(_minText.value, _maxText.value)
    }

    override fun onValidateMaxInput(input: String) {
        Timber.d("onValidateMaxInput input: $input")
        _maxText.value = InputUtils.validatePriceInput(input, _maxText.value)
        _inputUiState.value = AlertInputUiState.Normal(_minText.value, _maxText.value)
    }

    private fun fetchPrice(
        coinId: String,
        temporaryAlert: Alert? = null,
        forceRefresh: Boolean = false,
        onResult: ((String) -> Unit)? = null
    ) {
        Timber.d("fetchPrices forceRefresh: $forceRefresh")
        viewModelScope.launch {
            pricesRemoteDataSource.fetchPrices(
                coinIds = coinId,
                currencies = Constants.CURRENCIES
            ).collect { result ->
                Timber.d("result: $result")
                _priceUiState.value = when (result.status) {
                    Result.Status.LOADING -> {
                        if (priceUiState.value is AlertPriceUiState.Loaded) {
                            priceUiState.value
                        } else {
                            AlertPriceUiState.Loading(forceRefresh)
                        }
                    }
                    Result.Status.SUCCESS -> {
                        result.data?.getCoins()?.firstOrNull()?.let {
                            temporaryAlert?.let { alert -> checkAlert(alert, it, onResult) }
                            AlertPriceUiState.Loaded(it)
                        } ?: AlertPriceUiState.Empty
                    }
                    Result.Status.ERROR -> AlertPriceUiState.Error(result.message ?: "Error")
                }
            }
        }
    }

    private fun checkAlert(
        temporaryAlert: Alert,
        coin: Coin,
        onResult: ((String) -> Unit)? = null
    ) {
        Timber.d("checkAlert alert: $temporaryAlert coin: $coin")
        val price = coin.coinPrice?.getPriceOrNull(Constants.CURRENCIES) ?: return

        if (temporaryAlert.isPriceOutOfRange(price)) {
            val alert = Alert(
                min = temporaryAlert.min,
                max = temporaryAlert.max,
                coinId = coin.coinId,
                coinPrice = coin.coinPrice
            )
            viewModelScope.launch {
                alertDao.insert(alert)
            }
            Timber.d("price out of range inserted to DB")
            onResult?.let {
                it(
                    getApplication<CryptoTrackerApplication>()
                        .getString(R.string.notification_out_range)
                )
            }
        } else {
            Timber.d("Price in range")
            onResult?.let {
                it(
                    getApplication<CryptoTrackerApplication>()
                        .getString(R.string.notification_in_range)
                )
            }
        }
    }
}

sealed class AlertPriceUiState {
    object Empty : AlertPriceUiState()
    class Loading(val forceRefresh: Boolean) : AlertPriceUiState()
    class Loaded(val coin: Coin) : AlertPriceUiState()
    class Error(val message: String) : AlertPriceUiState()
}

sealed class AlertInputUiState(val min: String, val max: String) {
    class Normal(min: String = "", max: String = "") : AlertInputUiState(min, max)
    class Error(
        min: String = "",
        max: String = "",
        val minMaxError: String? = null,
    ) : AlertInputUiState(min, max)
}

interface UserInputCallbacks {
    fun onValidateMinInput(input: String)
    fun onValidateMaxInput(input: String)
    fun onCheckAlert(onResult: (String) -> Unit)
}