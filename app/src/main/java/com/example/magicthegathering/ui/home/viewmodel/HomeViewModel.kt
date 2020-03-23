package com.example.magicthegathering.ui.home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.magicthegathering.network.MagicApi
import com.example.magicthegathering.network.RetrofitService
import com.example.magicthegathering.network.models.Card
import com.example.magicthegathering.repository.remote.CardRepository
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.math.ceil

class HomeViewModel: ViewModel() {

    private val parentJob = Job()
    private val coroutineContext: CoroutineContext get() = parentJob + Dispatchers.Default
    private val scope = CoroutineScope(coroutineContext)
    private val cardRepository: CardRepository = CardRepository(RetrofitService.createService(MagicApi::class.java))
    private var card = ArrayList<Card>()

    val homeLiveData = MutableLiveData<MutableList<Card>>()
    fun getCards() {
        scope.launch {
                val sets = cardRepository.getSets()?.sortedByDescending { it.releaseDate }
                val header = cardRepository.getHeader(sets?.get(0)?.code.toString(), 1)
                val totalPage = calculateTotalPage(header)

                for (page in 0 until totalPage+1) {
                    val response = cardRepository.getCards(sets?.get(0)?.code.toString(), page)
                    card.addAll(response!!)
                }
                card.sortBy { it.name }
                homeLiveData.postValue(card)
        }
    }

    private fun calculateTotalPage(header: String): Int {
        return ceil(header.toDouble().div(100)).toInt()
    }



    fun cancelRequest() = coroutineContext.cancel()
}