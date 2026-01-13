package com.example.adoptacaterpillar.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adoptacaterpillar.R
import com.example.adoptacaterpillar.data.repository.CatFactRepository
import com.example.adoptacaterpillar.domain.model.CatFact
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CatFactViewModel : ViewModel() {
    private val repository = CatFactRepository()

    private val catImages = listOf(
        R.drawable.cat1,
        R.drawable.cat2,
        R.drawable.cat3,
        R.drawable.cat4
    )

    private val _currentImageIndex = MutableStateFlow(0)
    private val _currentImageRes = MutableStateFlow(catImages[0])
    val currentImageRes: StateFlow<Int> = _currentImageRes.asStateFlow()

    private val _currentFact = MutableStateFlow<CatFact?>(null)
    val currentFact: StateFlow<CatFact?> = _currentFact.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        loadNewFact()
    }

    fun loadNewFact() {
        viewModelScope.launch {
            _isLoading.value = true

            // Change image
            _currentImageIndex.value = (_currentImageIndex.value + 1) % catImages.size
            _currentImageRes.value = catImages[_currentImageIndex.value]

            // Load the fact from API
            try {
                val result = repository.getRandomFact()
                if (result.isSuccess) {
                    _currentFact.value = result.getOrNull()
                } else {
                    _currentFact.value = CatFact(fact = "oops")
                }
            } catch (e: Exception) {
                _currentFact.value = CatFact(fact = "oops")
            }

            _isLoading.value = false
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}
