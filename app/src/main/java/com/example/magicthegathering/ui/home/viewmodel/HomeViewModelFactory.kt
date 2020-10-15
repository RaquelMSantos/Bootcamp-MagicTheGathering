package com.example.magicthegathering.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.magicthegathering.repository.remote.GetCardsUseCase
import kotlinx.coroutines.CoroutineDispatcher
import java.lang.IllegalArgumentException

class HomeViewModelFactory constructor(
        private val mainDispather: CoroutineDispatcher,
        private val ioDispatcher: CoroutineDispatcher,
        private val getCardsUseCase: GetCardsUseCase
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(HomeViewModel::class.java)){
            HomeViewModel(mainDispather, ioDispatcher, getCardsUseCase) as T
        }else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}