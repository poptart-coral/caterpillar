package com.example.adoptacaterpillar.domain.repository

import com.example.adoptacaterpillar.domain.model.Cat

interface CatRepository {
    fun getDummyCats(): List<Cat>
    fun getRandomCatUrl(): String
}
