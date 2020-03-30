package com.example.magicthegathering.ui.favorites.fragment.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.magicthegathering.network.models.Card
import com.example.magicthegathering.repository.local.AppDatabase
import com.example.magicthegathering.ui.favorites.fragment.FavoritesRepository
import com.example.magicthegathering.utils.CardRow
import com.example.magicthegathering.utils.RowType
import kotlinx.coroutines.launch

class FavoritesViewModel(application: Application): AndroidViewModel(application) {

    private val favoritesRepository: FavoritesRepository
    val allCards: LiveData<List<Card>>

    init {
        val cardsDao = AppDatabase.getInstance(application, viewModelScope).cardDao()
        favoritesRepository = FavoritesRepository(cardsDao)
        allCards = favoritesRepository.allCards
    }

    fun insert(card: Card) = viewModelScope.launch {
        favoritesRepository.insert(card)
    }

    fun delete(name: String) = viewModelScope.launch {
        favoritesRepository.delete(name)
    }

    fun search(name: String): LiveData<Card>  {
        return favoritesRepository.search(name)
    }

    fun sortCards(cardList: ArrayList<Card>): ArrayList<CardRow> {
        val map = HashMap<String, ArrayList<Card>>()
        for (card in cardList) {
            var cards = map[card.setName]
            if (cards == null){
                cards = ArrayList()
                map[card.setName] = cards
            }
            cards.add(card)
        }
        val newCard = ArrayList<CardRow>()
        for ((key, value) in map) {
            newCard.add(CardRow(RowType.HEADER, null, key))
            value.mapTo(newCard) {
                CardRow(RowType.CARD, it, null)
            }
        }
        return newCard
    }

}