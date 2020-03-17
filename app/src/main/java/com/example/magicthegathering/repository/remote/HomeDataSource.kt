package com.example.magicthegathering.repository.remote

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.example.magicthegathering.models.Card
import com.example.magicthegathering.data.MagicApi
import com.example.magicthegathering.data.RetrofitService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.lang.Exception

class HomeDataSource(private val viewModelScope: CoroutineScope): PageKeyedDataSource<Int, Card>() {

    val PAGE_SIZE = 20
    val FIRST_PAGE = 1
    val set = "10E"
    var magicApi: MagicApi

    init {
        magicApi = RetrofitService.createService(MagicApi::class.java)
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Card>) {

        viewModelScope.launch {
            try {
                val response = magicApi.getCards(set, FIRST_PAGE, PAGE_SIZE)
                when {
                    response.isSuccessful -> {
                        callback.onResult(response.body()!!.listCard, null, FIRST_PAGE + 1)
                    }
                }
            } catch (exception: Exception) {
                Log.e("repository->Home", "" + exception.message)
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Card>) {
        viewModelScope.launch {
            try {
                val response = magicApi.getCards(set, params.key, PAGE_SIZE)
                when {
                    response.isSuccessful -> {
                        val key: Int?
                        if (response.body()?.listCard?.isNotEmpty()!!) key = params.key + 1
                        else key = null
                        callback.onResult(response.body()!!.listCard, key)
                    }
                }
            } catch (exception: Exception) {
                Log.e("repository->Cards", "" + exception.message)
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Card>) {
        viewModelScope.launch {
            try {
                val response = magicApi.getCards(set, params.key, PAGE_SIZE)
                val key: Int?
                if (params.key > 1) key = params.key - 1
                else key = null
                when {
                    response.isSuccessful -> {
                        callback.onResult(response.body()!!.listCard, key)
                    }
                }
            } catch (exception: Exception) {
                Log.e("repository->Card", "" + exception.message)
            }
        }
    }

    override fun invalidate() {
        super.invalidate()
        viewModelScope.cancel()
    }
}
