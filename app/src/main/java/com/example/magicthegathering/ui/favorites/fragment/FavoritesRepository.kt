package com.example.magicthegathering.ui.favorites.fragment

import androidx.lifecycle.LiveData
import com.example.magicthegathering.network.models.Card
import com.example.magicthegathering.repository.local.CardDao

class FavoritesRepository(private val cardDao: CardDao) {
    val allCards: LiveData<List<Card>> = cardDao.getCards()

    suspend fun insert(card: Card){
        cardDao.insertCard(card)
    }

    suspend fun delete(name: String) {
        cardDao.deleteCard(name)
    }
    fun search(name: String): LiveData<Card>{
        return cardDao.searchCard(name)
    }
}