package com.example.adoptacaterpillar.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.adoptacaterpillar.data.repository.CatRepositoryImpl
import com.example.adoptacaterpillar.domain.model.Cat
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CatViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = CatRepositoryImpl(application.applicationContext)

    private val _cats = MutableStateFlow<List<Cat>>(emptyList())
    val cats: StateFlow<List<Cat>> = _cats.asStateFlow()

    private val _refreshTrigger = MutableStateFlow(0)
    val refreshTrigger: StateFlow<Int> = _refreshTrigger.asStateFlow()

    init {
        _cats.value = repository.getDummyCats()
    }

    fun refreshRandomCat() {
        _refreshTrigger.value++
    }

    fun getCatById(id: String): Cat? {
        return _cats.value.find { it.id == id }
    }
}
