package com.example.adoptacaterpillar.domain.repository

import com.example.adoptacaterpillar.data.local.entity.CachedRandomCat
import com.example.adoptacaterpillar.domain.model.Breed
import kotlinx.coroutines.flow.Flow

interface CatRepository {
    fun getCachedBreedsFlow(): Flow<List<Breed>>
    suspend fun refreshBreeds(): Result<Unit>
    suspend fun downloadRandomCat(): Result<String>
    fun getLatestCatFlow(): Flow<CachedRandomCat?>
}
