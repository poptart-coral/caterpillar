package com.example.adoptacaterpillar.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cached_random_cats")
data class CachedRandomCat(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val imageFilePath: String,
    val downloadedAt: Long = System.currentTimeMillis()
)
