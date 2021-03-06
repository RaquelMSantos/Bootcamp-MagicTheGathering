package com.example.magicthegathering.network

import com.example.magicthegathering.network.models.CardResponse
import com.example.magicthegathering.network.models.SetResponse
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MagicApi {

    @GET("/v1/sets")
    fun getSetsAsync(): Call<SetResponse>

    @GET("/v1/cards")
    fun getCardsAsync(@Query("set") set: String, @Query("page") page: Int)
            : Call<CardResponse>

}