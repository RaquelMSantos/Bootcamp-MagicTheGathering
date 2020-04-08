package com.example.magicthegathering.ui.home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.magicthegathering.utils.LiveDataResult
import com.example.magicthegathering.network.models.Card
import com.example.magicthegathering.network.models.SetModel
import com.example.magicthegathering.repository.remote.GetCardsUseCase
import com.example.magicthegathering.utils.CardRow
import com.example.magicthegathering.utils.RowType
import kotlinx.coroutines.*
import okhttp3.Headers
import java.lang.Exception
import kotlin.math.ceil

class HomeViewModel(
        mainDispacher: CoroutineDispatcher,
        ioDispatcher: CoroutineDispatcher,
        private val getSetsUseCase: GetCardsUseCase
): ViewModel() {

    private val job = SupervisorJob()
    val homeLiveData = MutableLiveData<LiveDataResult<MutableList<Card>>>()
    private val uiScope = CoroutineScope(mainDispacher + job)
    private val ioScope = CoroutineScope(ioDispatcher + job)

    private var cardList = ArrayList<Card>()

    fun getCards(countPage: Int) {
        uiScope.launch {
            homeLiveData.value = LiveDataResult.loading()

            try {
                val sets = fetchSets()
                val header = fetchHeaders(sets, countPage)
                val totalPage = calculateTotalPage(header)

                cardList.clear()

                for (page in 1 until totalPage+1) {
                    val response = fetchCards(sets, countPage, page)
                    cardList.addAll(response!!)
                }

                cardList.sortBy { it.name }

                homeLiveData.value = LiveDataResult.success(cardList)

            }catch (e: Exception) {
                homeLiveData.value = LiveDataResult.error(e)
            }

        }
    }

    suspend fun fetchSets(): List<SetModel> {
        return ioScope.async {
            return@async getSetsUseCase.executeSet()
        }.await()
    }

    suspend fun fetchHeaders(sets: List<SetModel>, countPage: Int): Headers{
        return ioScope.async {
            return@async getSetsUseCase.executeHeaders(sets, countPage)
        }.await()
    }

    suspend fun fetchCards(sets: List<SetModel>, countPage: Int, page: Int): MutableList<Card>? {
        return ioScope.async {
            return@async getSetsUseCase.executeCards(sets, countPage, page)
        }.await()
    }

    override fun onCleared() {
        super.onCleared()
        this.job.cancel()
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
}