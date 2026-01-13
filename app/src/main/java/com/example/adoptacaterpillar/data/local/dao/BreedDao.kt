package com.example.adoptacaterpillar.data.local.dao

import androidx.room.*
import com.example.adoptacaterpillar.data.local.entity.CachedBreed
import kotlinx.coroutines.flow.Flow

@Dao
interface BreedDao {

    @Query("SELECT * FROM cached_breeds ORDER BY name ASC")
    fun getAllBreeds(): Flow<List<CachedBreed>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(breeds: List<CachedBreed>)

    @Query("DELETE FROM cached_breeds")
    suspend fun deleteAll()

    @Query("SELECT COUNT(*) FROM cached_breeds")
    suspend fun getCount(): Int
}
