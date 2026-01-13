package com.example.adoptacaterpillar.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.adoptacaterpillar.domain.model.Cat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CatViewModel @Inject constructor() : ViewModel() {

    private val _cats = MutableStateFlow<List<Cat>>(emptyList())

    private val _refreshTrigger = MutableStateFlow(0)
    val refreshTrigger: StateFlow<Int> = _refreshTrigger.asStateFlow()

    init {
        _cats.value = emptyList()
    }

    fun refreshRandomCat() {
        _refreshTrigger.value++
    }
}
