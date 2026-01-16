package com.example.adoptacaterpillar.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adoptacaterpillar.data.NetworkObserver
import com.example.adoptacaterpillar.data.repository.CatRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatViewModel @Inject constructor(
    private val repository: CatRepositoryImpl,
    private val networkObserver: NetworkObserver
) : ViewModel() {
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _currentRandomCatPath = MutableStateFlow<String?>(null)
    val currentRandomCatPath: StateFlow<String?> = _currentRandomCatPath.asStateFlow()

    private val _isLoadingRandomCat = MutableStateFlow(false)
    val isLoadingRandomCat: StateFlow<Boolean> = _isLoadingRandomCat.asStateFlow()


    private val _isOnline = MutableStateFlow(false)
    val isOnline: StateFlow<Boolean> = _isOnline.asStateFlow()

    private val _cachedCatPath = MutableStateFlow<String?>(null)

    init {
        // Observe network
        viewModelScope.launch {
            networkObserver.isOnline.collect { isConnected ->
                _isOnline.value = isConnected
            }
        }

        // Observe cache
        viewModelScope.launch {
            repository.getLatestCatFlow().collect { cachedCat ->
                cachedCat?.let {
                    _cachedCatPath.value = it.imageFilePath
                    // Update current cat path if it's null
                    if (_currentRandomCatPath.value == null) {
                        _currentRandomCatPath.value = it.imageFilePath
                    }
                }
            }
        }
    }

    fun downloadNewRandomCat() {
        viewModelScope.launch {
            if (!isOnline.value) {
                _error.value = "No internet connection"
                return@launch
            }
            _isLoadingRandomCat.value = true
            _error.value = null

            repository.downloadRandomCat().fold(
                onSuccess = { path ->
                    _currentRandomCatPath.value = path
                },
                onFailure = { exception ->
                    Log.e("CatViewModel", "Error: ${exception.message}")
                    _error.value = "Failed to download cat"
                }
            )

            _isLoadingRandomCat.value = false
        }
    }
}
