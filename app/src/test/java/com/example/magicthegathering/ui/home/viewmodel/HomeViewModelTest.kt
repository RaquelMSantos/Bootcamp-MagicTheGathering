package com.example.magicthegathering.ui.home.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.magicthegathering.network.models.Card
import com.example.magicthegathering.network.models.SetModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before

import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock

@RunWith(JUnit4::class)
class HomeViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @MockK
    lateinit var viewModel: HomeViewModel
    val dispatcher = Dispatchers.Unconfined


    @Mock
    private lateinit var homeLiveDataObserver: Observer<MutableList<Card>>

    @Before
    fun setup(){
        MockKAnnotations.init(this)
       // viewModel = HomeViewModel()
    }

//    @Test
//    fun givenHomeViewModel_whenViewModelGetCard_shouldCallTheRepository() {
//        coEvery { cardRepository.getSets() } returns MutableList<SetModel>(2){
//            SetModel ("10E", "Tenth Edition", "2007-07-13")
//            SetModel("2ED", "Unlimited Edition", "1993-12-01")
//        }
//
//        viewModel.homeLiveData.observeForever{}
//        runBlocking { viewModel.fetchSets() }
//
//        assert(viewModel.homeLiveData.value != null)
////        assert(viewModel.homeLiveData.value == MutableList.)
//
//    }

//    @Test
//    fun givenHomeViewModel_whenViewModelGetCard_shouldCallTheRepository() {
//        val mockedSetList = Any()
//
//        runBlocking { whenever(cardRepository.getSets()).then(mockedSetList as Answer<*>?)}
//
//        runBlocking { viewModel.fetchSets() }
//
//        verify {
//            homeLiveDataObserver.onChanged(Output.Success(mockedSetList))
//        }
//    }
}