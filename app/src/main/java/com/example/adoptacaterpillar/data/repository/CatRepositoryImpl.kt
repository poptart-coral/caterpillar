package com.example.adoptacaterpillar.data.repository

import android.content.Context
import android.util.Log
import com.example.adoptacaterpillar.data.local.AppDatabase
import com.example.adoptacaterpillar.data.local.entity.CachedBreed
import com.example.adoptacaterpillar.data.local.entity.CachedRandomCat
import com.example.adoptacaterpillar.data.remote.api.CatBreedsService
import com.example.adoptacaterpillar.data.remote.api.CatFactsApiService
import com.example.adoptacaterpillar.domain.model.Breed
import com.example.adoptacaterpillar.domain.model.CatFact
import com.example.adoptacaterpillar.domain.repository.CatRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.io.File
import java.net.URL
import javax.inject.Inject

class CatRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val breedApiService: CatBreedsService,
    private val factsApiService: CatFactsApiService
) : CatRepository {
    private val breedDao = AppDatabase.getDatabase(context).breedDao()
    private val randomCatDao = AppDatabase.getDatabase(context).randomCatDao()

    override fun getLatestCatFlow(): Flow<CachedRandomCat?> {
        return randomCatDao.getLatestCatFlow()
    }
    override suspend fun downloadRandomCat(): Result<String> = withContext(Dispatchers.IO) {
        try {
            val file = File(context.filesDir, "cat_${System.currentTimeMillis()}.jpg")
            URL("https://cataas.com/cat").openStream().use { it.copyTo(file.outputStream()) }
            randomCatDao.insert(CachedRandomCat(imageFilePath = file.absolutePath))
            randomCatDao.keepOnlyRecent()
            Result.success(file.absolutePath)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getCachedBreedsFlow(): Flow<List<Breed>> {
        return breedDao.getAllBreeds().map { cachedList ->
            cachedList.map { it.toBreed() }
        }
    }

    override suspend fun refreshBreeds(): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            Log.d("CatRepo", "Fetching breeds from API...")
            // TODO use serializable
            val breedsDto = breedApiService.getBreeds()

            val cachedBreeds = breedsDto.map { dto ->
                CachedBreed(
                    id = dto.id,
                    name = dto.name,
                    temperament = dto.temperament,
                    description = dto.description,
                    lifeSpan = dto.lifeSpan,
                    origin = dto.origin
                )
            }

            breedDao.deleteAll()
            breedDao.insertAll(cachedBreeds)

            Result.success(Unit)
        } catch (e: Exception) {
            Log.e("CatRepo", "Error: ${e.message}", e)
            Result.failure(e)
        }
    }

    suspend fun getRandomFact(): Result<CatFact> = withContext(Dispatchers.IO) {
        try {
            val factResponse = factsApiService.getRandomFact()
            val fact = CatFact(factResponse.data.first())
            Result.success(fact)
        } catch (e: Exception) {
            Log.e("CatRepo", "Fact error: ${e.message}")
            Result.failure(e)
        }
    }

    private fun CachedBreed.toBreed() = Breed(
        id = id,
        name = name,
        temperament = temperament,
        description = description,
        lifeSpan = lifeSpan,
        origin = origin
    )
}
