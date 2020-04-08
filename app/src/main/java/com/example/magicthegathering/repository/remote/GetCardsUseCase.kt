package com.example.magicthegathering.repository.remote

import com.example.magicthegathering.network.models.Card
import com.example.magicthegathering.network.models.SetModel
import okhttp3.Headers

class GetCardsUseCase(private val cardRepository: CardRepository) {
    suspend fun executeSet(): List<SetModel> {
        return this.cardRepository.fetchSets().sortedByDescending { it.releaseDate }
    }

    suspend fun executeHeaders(sets: List<SetModel>?, countPage: Int): Headers {
        return this.cardRepository.fetchHeaders(sets?.get(countPage)?.code.toString(), countPage)
    }

    suspend fun executeCards(sets: List<SetModel>?, countPage: Int, page: Int): MutableList<Card>? {
        return this.cardRepository.fetchCards(sets?.get(countPage)?.code.toString(), page)
    }
}