package com.example.adoptacaterpillar.domain.model

data class Cat(
    val id: String,
    val name: String,
    val breed: String,
    val imageUrl: String,
    val description: String,
    val age: String = "Adulte",
    val gender: String = "Inconnu",
    val location: String = "Montpellier",
    val shelter: String = "SPA"
)
