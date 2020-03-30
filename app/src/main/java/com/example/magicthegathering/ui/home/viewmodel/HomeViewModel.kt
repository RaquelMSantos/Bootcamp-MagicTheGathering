package com.example.magicthegathering.ui.home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.magicthegathering.network.MagicApi
import com.example.magicthegathering.network.RetrofitService
import com.example.magicthegathering.network.models.Card
import com.example.magicthegathering.repository.remote.CardRepository
import com.example.magicthegathering.utils.CardRow
import com.example.magicthegathering.utils.RowType
import kotlinx.coroutines.*
import okhttp3.Headers
import kotlin.coroutines.CoroutineContext
import kotlin.math.ceil

class HomeViewModel: ViewModel() {

    private val parentJob = Job()
    private val coroutineContext: CoroutineContext get() = parentJob + Dispatchers.Default
    private val scope = CoroutineScope(coroutineContext)
    private val cardRepository: CardRepository = CardRepository(RetrofitService.createService(MagicApi::class.java))
    private var cardList = ArrayList<Card>()

    val homeLiveData = MutableLiveData<MutableList<Card>>()
    fun getCards(countPage: Int) {
        scope.launch {
                val sets = cardRepository.getSets()?.sortedByDescending { it.releaseDate }
                val header = cardRepository.getHeader(sets?.get(countPage)?.code.toString(), countPage)
                val totalPage = calculateTotalPage(header)

                for (page in 1 until totalPage+1) {
                    val response = cardRepository.getCards(sets?.get(countPage)?.code.toString(), page)
                    cardList.addAll(response!!)
                }
                cardList.sortBy { it.name }

                homeLiveData.postValue(cardList)
        }
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

    private fun calculateTotalPage(header: Headers): Int {
        val headerDouble = (header["total-count"]!!.toInt() / header["page-size"]!!.toDouble())
        return ceil(headerDouble).toInt()
    }

    fun cancelRequest() = coroutineContext.cancel()
}