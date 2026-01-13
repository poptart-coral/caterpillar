package com.example.adoptacaterpillar.data.remote.api

import com.example.adoptacaterpillar.domain.model.CatFactResponse
import retrofit2.http.GET

interface CatFactsApiService {
    @GET("/")
    suspend fun getRandomFact(): CatFactResponse
}
