package com.example.magicthegathering.repository.remote

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.example.magicthegathering.models.Card
import kotlinx.coroutines.CoroutineScope

class HomeDataSourceFactory(private val viewModelScope: CoroutineScope): DataSource.Factory<Int, Card>() {

    private val homeCardLiveDataSource: MutableLiveData<PageKeyedDataSource<Int, Card>> = MutableLiveData()

    override fun create(): DataSource<Int, Card> {
        val homeDataSource =
            HomeDataSource(
                viewModelScope
            )
        homeCardLiveDataSource.postValue(homeDataSource)

        return homeDataSource
    }

    fun getHomeLiveDataSource(): MutableLiveData<PageKeyedDataSource<Int, Card>>? {
        return homeCardLiveDataSource

    }

}
