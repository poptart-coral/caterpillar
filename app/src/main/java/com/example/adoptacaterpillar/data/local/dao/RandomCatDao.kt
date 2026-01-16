package com.example.adoptacaterpillar.data.local.dao

import androidx.room.*
import com.example.adoptacaterpillar.data.local.entity.CachedRandomCat
import kotlinx.coroutines.flow.Flow

@Dao
interface RandomCatDao {
    @Query("SELECT * FROM cached_random_cats ORDER BY downloadedAt DESC LIMIT 1")
    fun getLatestCatFlow(): Flow<CachedRandomCat?>

    @Insert
    suspend fun insert(cat: CachedRandomCat)

    @Query("DELETE FROM cached_random_cats WHERE id NOT IN (SELECT id FROM cached_random_cats ORDER BY downloadedAt DESC LIMIT 10)")
    suspend fun keepOnlyRecent()
}
