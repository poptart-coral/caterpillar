package com.example.adoptacaterpillar.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class CatImageDto(
    val id: String,
    val url: String,
    val breeds: List<BreedDto> = emptyList()
)