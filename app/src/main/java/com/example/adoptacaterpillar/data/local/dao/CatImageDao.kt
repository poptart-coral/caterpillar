package com.example.adoptacaterpillar.data.local.dao

import androidx.room.*
import com.example.adoptacaterpillar.data.local.entity.CachedCatImage
import kotlinx.coroutines.flow.Flow

@Dao
interface CatImageDao {

    @Query("SELECT * FROM cached_cats ORDER BY downloadedAt DESC")
    fun getAllCats(): Flow<List<CachedCatImage>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(cats: List<CachedCatImage>)

    @Query("DELETE FROM cached_cats")
    suspend fun deleteAll()

    @Query("SELECT COUNT(*) FROM cached_cats")
    suspend fun getCount(): Int
}