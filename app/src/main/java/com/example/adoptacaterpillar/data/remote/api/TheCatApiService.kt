package com.example.adoptacaterpillar.data.remote.api

import com.example.adoptacaterpillar.data.remote.dto.BreedDto
import com.example.adoptacaterpillar.data.remote.dto.CatImageDto
import retrofit2.http.GET
import retrofit2.http.Query

interface TheCatApiService {

    @GET("images/search")
    suspend fun searchImages(
        @Query("has_breeds") hasBreeds: Int = 1,
        @Query("limit") limit: Int = 20
    ): List<CatImageDto>

    @GET("breeds")
    suspend fun getBreeds(): List<BreedDto>
}