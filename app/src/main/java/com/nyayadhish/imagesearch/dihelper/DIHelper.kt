package com.nyayadhish.imagesearch.dihelper

import android.content.Context
import com.nyayadhish.imagesearch.data.DataBase
import com.nyayadhish.imagesearch.data.SearchResultRepository
import com.nyayadhish.imagesearch.viewmodel.ResultListViewModelFactory

object DIHelper {
    fun provideSearchViewModelFactory(context: Context): ResultListViewModelFactory {
        val quoteRepository = SearchResultRepository.getInstance(DataBase.getInstance(context).searchResultDao)
        return ResultListViewModelFactory(quoteRepository)
    }

}