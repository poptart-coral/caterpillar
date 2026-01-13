package com.example.adoptacaterpillar.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adoptacaterpillar.data.repository.CatRepositoryImpl
import com.example.adoptacaterpillar.domain.model.Breed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BreedViewModel @Inject constructor(
    private val repository: CatRepositoryImpl
) : ViewModel() {

    private val _breeds = MutableStateFlow<List<Breed>>(emptyList())
    val breeds: StateFlow<List<Breed>> = _breeds.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        loadBreeds()
    }

    fun loadBreeds() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            repository.getBreeds().fold(
                onSuccess = { breedsList ->
                    _breeds.value = breedsList
                    Log.d("BreedViewModel", "Loaded ${breedsList.size} breeds")
                },
                onFailure = { exception ->
                    Log.e("BreedViewModel", "Error: ${exception.message}")
                    _error.value = exception.message ?: "Loading error"
                }
            )

            _isLoading.value = false
        }
    }
}