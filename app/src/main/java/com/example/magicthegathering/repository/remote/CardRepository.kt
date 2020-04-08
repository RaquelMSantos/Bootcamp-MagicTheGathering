package com.example.magicthegathering.repository.remote

import com.example.magicthegathering.network.models.Card
import com.example.magicthegathering.network.models.SetModel
import okhttp3.Headers

interface CardRepository {
    suspend fun fetchSets(): List<SetModel>
    suspend fun fetchHeaders(set: String, page: Int): Headers
    suspend fun fetchCards(set: String, page: Int): MutableList<Card>
}