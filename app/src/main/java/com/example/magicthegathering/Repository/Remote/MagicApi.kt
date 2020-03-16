package com.example.magicthegathering.Repository.Remote

import com.example.magicthegathering.Models.Card
import com.example.magicthegathering.Models.Set
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MagicApi {

    @GET("/sets")
    suspend fun getSets(): Response<List<Set>>

    @GET("/cards/{set}")
    suspend fun getCards(@Path("set") set: String): Response<List<Card>>

}