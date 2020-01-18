package com.nyayadhish.imagesearch.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nyayadhish.imagesearch.data.SearchResultRepository

@Suppress("UNCHECKED_CAST")
class ResultListViewModelFactory (
    private val repository: SearchResultRepository):ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ResultListViewModel(repository) as T
    }

}