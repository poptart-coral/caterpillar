package com.example.adoptacaterpillar.data.repository

import android.content.Context
import android.util.Log
import com.example.adoptacaterpillar.data.local.AppDatabase
import com.example.adoptacaterpillar.data.local.entity.CachedCatImage
import com.example.adoptacaterpillar.domain.model.Cat
import com.example.adoptacaterpillar.domain.repository.CatRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.io.File
import java.net.URL
import java.util.UUID

class CatRepositoryImpl(private val context: Context) : CatRepository {
    private val dao = AppDatabase.getDatabase(context).catImageDao()

    override fun getDummyCats(): List<Cat> {
        return listOf(
            Cat(UUID.randomUUID().toString(), "Whiskers", "Tabby", "local_cat_1", "A very friendly tabby cat."),
            Cat(UUID.randomUUID().toString(), "Luna", "Siamese", "local_cat_2", "Luna loves to meow and play."),
            Cat(UUID.randomUUID().toString(), "Oliver", "Maine Coon", "local_cat_3", "Oliver is a large and fluffy cat."),
            Cat(UUID.randomUUID().toString(), "Leo", "Bengal", "local_cat_4", "Leo is very active and playful.")
        )
    }

    // Returns image in cache (for offline first)
    fun getCachedRandomCats(): Flow<List<CachedCatImage>> {
        return dao.getAllRandomCats()
    }

    // Download and save a new image
    suspend fun downloadAndCacheRandomCat(): Result<String> = withContext(Dispatchers.IO) {
        try {
            val url = "https://cataas.com/cat}"
            val fileName = "random_cat_${System.currentTimeMillis()}.jpg"
            val file = File(context.filesDir, fileName)

            // Download the image
            URL(url).openStream().use { input ->
                file.outputStream().use { output ->
                    input.copyTo(output)
                }
            }

            // Save in Room
            val cachedImage = CachedCatImage(
                imageFilePath = file.absolutePath,
                downloadedAt = System.currentTimeMillis(),
                isRandomCat = true
            )
            dao.insert(cachedImage)

            Log.d("CatRepo", "Image downloaded and cached in ${file.absolutePath}")
            Result.success(file.absolutePath)
        } catch (e: Exception) {
            Log.e("CatRepo", "Download error: ${e.message}")
            Result.failure(e)
        }
    }

    // Get the last cached cat image
    suspend fun getLatestCachedCat(): String? = withContext(Dispatchers.IO) {
        dao.getLatestRandomCat()?.imageFilePath
    }

    override fun getRandomCatUrl(): String {
        return "https://cataas.com/cat?timestamp=${System.currentTimeMillis()}"
    }
}
