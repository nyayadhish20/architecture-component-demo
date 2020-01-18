package com.nyayadhish.imagesearch.viewmodel

import androidx.lifecycle.ViewModel
import com.nyayadhish.imagesearch.data.Items
import com.nyayadhish.imagesearch.data.SearchResultRepository

class ResultListViewModel (private val searchResultRepository: SearchResultRepository): ViewModel(){

    fun getSearchResult(searchQuery: String, startIndex: String) = searchResultRepository.getSearchResult(searchQuery,startIndex)

    fun updateSearchResult(mList: List<Items>) = searchResultRepository.updateSearchResult(mList)

    fun getImages() = searchResultRepository.getImages()



}