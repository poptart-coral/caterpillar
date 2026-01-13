package com.example.adoptacaterpillar.data.local.dao

import androidx.room.*
import com.example.adoptacaterpillar.data.local.entity.CachedRandomCat

@Dao
interface RandomCatDao {
    @Query("SELECT * FROM cached_random_cats ORDER BY downloadedAt DESC LIMIT 1")
    suspend fun getLatestCat(): CachedRandomCat?

    @Insert
    suspend fun insert(cat: CachedRandomCat)

    @Query("DELETE FROM cached_random_cats WHERE id NOT IN (SELECT id FROM cached_random_cats ORDER BY downloadedAt DESC LIMIT 10)")
    suspend fun keepOnlyRecent()
}
