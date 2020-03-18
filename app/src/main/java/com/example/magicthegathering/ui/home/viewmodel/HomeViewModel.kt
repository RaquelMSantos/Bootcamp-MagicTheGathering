package com.example.magicthegathering.ui.home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.magicthegathering.network.MagicApi
import com.example.magicthegathering.network.RetrofitService
import com.example.magicthegathering.network.models.Card
import com.example.magicthegathering.repository.remote.CardRepository
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class HomeViewModel: ViewModel() {

    private val parentJob = Job()
    private val coroutineContext: CoroutineContext get() = parentJob + Dispatchers.Default
    private val scope = CoroutineScope(coroutineContext)
    private val cardRepository: CardRepository = CardRepository(RetrofitService.createService(MagicApi::class.java))

    val homeLiveData = MutableLiveData<MutableList<Card>>()
    fun getCards() {
        scope.launch {
            val sets = cardRepository.getSets()?.sortedByDescending { it.date }
            val card = cardRepository.getCards(sets?.get(0)?.code.toString())
            homeLiveData.postValue(card)
        }
    }

    fun cancelRequest() = coroutineContext.cancel()
}