package com.example.adoptacaterpillar.data.repository

import android.util.Log
import com.example.adoptacaterpillar.domain.model.CatFact
import com.example.adoptacaterpillar.domain.model.CatFactResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import java.net.URL

class CatFactRepository {
    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    suspend fun getRandomFact(): Result<CatFact> = withContext(Dispatchers.IO) {
        try {
            Log.d("CatFactRepo", "Fetching fact from API")

            val response = URL("https://meowfacts.herokuapp.com/").readText()
            Log.d("CatFactRepo", "Response: $response")

            val factResponse = json.decodeFromString<CatFactResponse>(response)
            val fact = CatFact(factResponse.data.first())

            Log.d("CatFactRepo", "Fact: $fact")
            Result.success(fact)
        } catch (e: Exception) {
            Log.e("CatFactRepo", "Error: ${e.message}")
            Result.failure(e)
        }
    }
}
