package com.example.adoptacaterpillar.data.remote.api

import com.example.adoptacaterpillar.data.remote.dto.BreedDto
import retrofit2.http.GET

interface CatBreedsService {
    @GET("breeds")
    suspend fun getBreeds(): List<BreedDto>
}
