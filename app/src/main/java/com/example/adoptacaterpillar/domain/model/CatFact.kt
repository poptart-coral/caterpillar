package com.example.adoptacaterpillar.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class CatFact(
    val fact: String
)

@Serializable
data class CatFactResponse(
    val data: List<String>
)
