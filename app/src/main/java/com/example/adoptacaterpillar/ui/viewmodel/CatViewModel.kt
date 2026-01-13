package com.example.adoptacaterpillar.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adoptacaterpillar.data.repository.CatRepositoryImpl
import com.example.adoptacaterpillar.domain.model.Cat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatViewModel @Inject constructor(
    private val repository: CatRepositoryImpl
) : ViewModel() {

    private val _cats = MutableStateFlow<List<Cat>>(emptyList())
    val cats: StateFlow<List<Cat>> = _cats.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _refreshTrigger = MutableStateFlow(0)
    val refreshTrigger: StateFlow<Int> = _refreshTrigger.asStateFlow()

    private val _currentRandomCatPath = MutableStateFlow<String?>(null)
    val currentRandomCatPath: StateFlow<String?> = _currentRandomCatPath.asStateFlow()

    private val _isLoadingRandomCat = MutableStateFlow(false)
    val isLoadingRandomCat: StateFlow<Boolean> = _isLoadingRandomCat.asStateFlow()

    private fun loadLatestRandomCat() {
        viewModelScope.launch {
            val path = repository.getLatestRandomCatPath()
            _currentRandomCatPath.value = path
        }
    }

    fun downloadNewRandomCat() {
        viewModelScope.launch {
            _isLoadingRandomCat.value = true

            repository.downloadRandomCat().fold(
                onSuccess = { path ->
                    _currentRandomCatPath.value = path
                    Log.d("CatViewModel", "New cat: $path")
                },
                onFailure = { exception ->
                    Log.e("CatViewModel", "Error: ${exception.message}")
                    _error.value = "Failed to download cat"
                }
            )

            _isLoadingRandomCat.value = false
        }
    }

    fun refreshRandomCat() {
        _refreshTrigger.value++
    }

    fun getCatById(id: String): Cat? {
        return _cats.value.find { it.id == id }
    }

    init {
        _cats.value = emptyList()
    }
}
