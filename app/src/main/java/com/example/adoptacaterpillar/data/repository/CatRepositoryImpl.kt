package com.example.adoptacaterpillar.data.repository

import android.content.Context
import android.util.Log
import com.example.adoptacaterpillar.data.local.AppDatabase
import com.example.adoptacaterpillar.data.remote.api.TheCatApiService
import com.example.adoptacaterpillar.domain.model.Breed
import com.example.adoptacaterpillar.domain.repository.CatRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CatRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val apiService: TheCatApiService
) : CatRepository {
    private val dao = AppDatabase.getDatabase(context).catImageDao()

    override fun getRandomCatUrl(): String {
        return "https://cataas.com/cat?timestamp=${System.currentTimeMillis()}"
    }

    override suspend fun getBreeds(): Result<List<Breed>> = withContext(Dispatchers.IO) {
        try {
            Log.d("CatRepo", "Fetching breeds from API...")
            val breedsDto = apiService.getBreeds()

            val breeds = breedsDto.map { dto ->
                Breed(
                    id = dto.id,
                    name = dto.name,
                    temperament = dto.temperament,
                    description = dto.description,
                    lifeSpan = dto.life_span,
                    origin = dto.origin
                )
            }

            Log.d("CatRepo", "Fetched ${breeds.size} breeds")
            Result.success(breeds)
        } catch (e: Exception) {
            Log.e("CatRepo", "Error fetching breeds: ${e.message}", e)
            Result.failure(e)
        }
    }
}
