package com.example.adoptacaterpillar.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class BreedDto(
    val id: String,
    val name: String,
    val temperament: String = "",
    val description: String = "",
    val lifeSpan: String = "",
    val origin: String = ""
)
