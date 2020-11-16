package com.example.catsgallery.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.thecatapi.com/v1/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface TheCatApiService {
    @GET("images/search")
    suspend fun getSearchItems(@Query("api_key") apiKey: String,
                               @Query("page") page: Int,
                               @Query("limit") limit: Int,
                               @Query("category_ids") categoryIds: List<Int>): List<TheCatSearchResponseItem>

    @GET("categories")
    suspend fun getCategories(): List<TheCatCategoryResponseItem>
}

object TheCatApi {
    val retrofitService : TheCatApiService by lazy { retrofit.create(TheCatApiService::class.java) }
}
