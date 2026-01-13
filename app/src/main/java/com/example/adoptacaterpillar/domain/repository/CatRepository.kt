package com.example.adoptacaterpillar.domain.repository

import com.example.adoptacaterpillar.domain.model.Breed

interface CatRepository {
    fun getRandomCatUrl(): String
    suspend fun getBreeds(): Result<List<Breed>>
}
