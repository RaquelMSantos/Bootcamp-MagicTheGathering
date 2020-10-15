package com.example.magicthegathering.repository.remote

import com.example.magicthegathering.network.MagicApi

class CardDatasource(private val magicApi: MagicApi): CardRepository {
    override suspend fun fetchSets() =
            this.magicApi.getSetsAsync().execute().body()?.listSetModels?.toMutableList()
                    ?: throw  NullPointerException("No body")

    override suspend fun fetchHeaders(set: String, page: Int) =
        this.magicApi.getCardsAsync(set, page).execute().headers()
                ?: throw  NullPointerException("No body")

    override suspend fun fetchCards(set: String, page: Int) =
            this.magicApi.getCardsAsync(set, page).execute().body()?.listCard?.toMutableList()
                    ?: throw  NullPointerException("No body")
}