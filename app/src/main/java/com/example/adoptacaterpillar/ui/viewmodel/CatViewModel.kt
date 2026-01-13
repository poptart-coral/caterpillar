package com.example.adoptacaterpillar.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.adoptacaterpillar.data.repository.CatRepositoryImpl
import com.example.adoptacaterpillar.domain.model.Cat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CatViewModel @Inject constructor(
    private val repository: CatRepositoryImpl
) : ViewModel() {

    private val _cats = MutableStateFlow<List<Cat>>(emptyList())
    val cats: StateFlow<List<Cat>> = _cats.asStateFlow()

    private val _refreshTrigger = MutableStateFlow(0)
    val refreshTrigger: StateFlow<Int> = _refreshTrigger.asStateFlow()

    init {
        _cats.value = emptyList()
    }

    fun refreshRandomCat() {
        _refreshTrigger.value++
    }

    fun getCatById(id: String): Cat? {
        return _cats.value.find { it.id == id }
    }
}
