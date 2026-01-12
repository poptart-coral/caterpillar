package com.example.adoptacaterpillar.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cached_cat_images")
data class CachedCatImage(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val imageFilePath: String,  // Chemin local du fichier
    val downloadedAt: Long,
    val isRandomCat: Boolean = true
)
