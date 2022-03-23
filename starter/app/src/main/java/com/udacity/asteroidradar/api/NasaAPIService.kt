package com.udacity.asteroidradar.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Constants.BASE_URL
import com.udacity.asteroidradar.PictureOfDay
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(
        MoshiConverterFactory.create(
            Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .build()

interface NasaAPIService {

    @GET("/neo/rest/v1/feed")
    suspend fun getAsteroidsState(@Query("api_key") api_key: String): String

    @GET("/planetary/apod")
    suspend fun getImageOfTheDay(@Query("api_key") api_key: String): PictureOfDay
}

object NasaAPI {
    val retrofitService : NasaAPIService by lazy { retrofit.create(NasaAPIService::class.java) }
}