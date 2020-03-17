package com.example.magicthegathering.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.example.magicthegathering.models.Card
import com.example.magicthegathering.repository.remote.HomeDataSource
import com.example.magicthegathering.repository.remote.HomeDataSourceFactory

class CardViewModel: ViewModel() {

    var homePagedList: LiveData<PagedList<Card>>? = null
    private var homeLiveDataSource: LiveData<PageKeyedDataSource<Int, Card>>? = null

    init {
        val homeDataSourceFactory = HomeDataSourceFactory(viewModelScope)
        homeLiveDataSource = homeDataSourceFactory.getHomeLiveDataSource()

        val config: PagedList.Config = (PagedList.Config.Builder()).setEnablePlaceholders(false)
            .setPageSize(
                HomeDataSource(viewModelScope).PAGE_SIZE).build()

        homePagedList = LivePagedListBuilder(homeDataSourceFactory, config).build()
    }

}