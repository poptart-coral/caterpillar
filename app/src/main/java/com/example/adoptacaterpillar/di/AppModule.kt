package com.example.adoptacaterpillar.di

import android.content.Context
import com.example.adoptacaterpillar.data.remote.api.CatBreedsService
import com.example.adoptacaterpillar.data.remote.api.CatFactsApiService
import com.example.adoptacaterpillar.data.repository.CatRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCatRepository(@ApplicationContext context: Context, apiService: CatBreedsService, catFactsApiService: CatFactsApiService): CatRepositoryImpl {
        return CatRepositoryImpl(context, apiService, catFactsApiService)
    }
}
