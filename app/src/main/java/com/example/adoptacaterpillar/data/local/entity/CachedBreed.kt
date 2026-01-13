package com.example.adoptacaterpillar.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cached_breeds")
data class CachedBreed(
    @PrimaryKey val id: String,
    val name: String,
    val temperament: String,
    val description: String,
    val lifeSpan: String,
    val origin: String,
    val downloadedAt: Long = System.currentTimeMillis()
)
