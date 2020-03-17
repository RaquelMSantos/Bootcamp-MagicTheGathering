package com.example.magicthegathering.data

import com.example.magicthegathering.models.Card
import com.example.magicthegathering.models.CardListCallback
import com.example.magicthegathering.models.Set
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MagicApi {

    @GET("/sets")
    suspend fun getSets(): Response<List<Set>>

    @GET("/v1/cards")
    suspend fun getCards(@Query("set") set: String,
                         @Query("page") page: Int,
                         @Query("per_page") noPages: Int): Response<CardListCallback>

}