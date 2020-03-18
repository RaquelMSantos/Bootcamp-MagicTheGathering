package com.example.magicthegathering.repository.remote

import com.example.magicthegathering.network.MagicApi
import com.example.magicthegathering.network.models.Card
import com.example.magicthegathering.network.models.SetModel

class CardRepository (private val magicApi: MagicApi): BaseRepository() {

    suspend fun getCards(set: String): MutableList<Card>? {
        return safeApiCall(
            call = {magicApi.getCardsAsync(set).await()},
            error = "Error cards"
        )?.listCard?.toMutableList()
    }

    suspend fun getSets(): MutableList<SetModel>? {
        return safeApiCall(
            call = {magicApi.getSetsAsync().await()},
            error = "Error sets"
        )?.listSetModels?.toMutableList()
    }
}