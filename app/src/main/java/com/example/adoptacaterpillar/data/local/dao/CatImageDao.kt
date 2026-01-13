package com.example.adoptacaterpillar.data.local.dao

import androidx.room.*
import com.example.adoptacaterpillar.data.local.entity.CachedCatImage
import kotlinx.coroutines.flow.Flow

@Dao
interface CatImageDao {
    @Query("SELECT * FROM cached_cat_images ORDER BY downloadedAt DESC LIMIT 1")
    suspend fun getLatestRandomCat(): CachedCatImage? //

    @Query("SELECT * FROM cached_cat_images ORDER BY downloadedAt DESC")
    fun getAllRandomCats(): Flow<List<CachedCatImage>>

    @Insert
    suspend fun insert(image: CachedCatImage)
}
