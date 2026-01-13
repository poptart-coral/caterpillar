package com.example.adoptacaterpillar.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cached_cats")
data class CachedCatImage(
    @PrimaryKey val id: String,
    val name: String,
    val breed: String,
    val imageUrl: String,
    val description: String,
    val age: String,
    val gender: String,
    val location: String,
    val shelter: String,
    val downloadedAt: Long = System.currentTimeMillis()
)