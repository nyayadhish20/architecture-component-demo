package com.nyayadhish.imagesearch.viewmodel

import androidx.lifecycle.ViewModel
import com.nyayadhish.imagesearch.data.SearchResultRepository

class ResultListViewModel (private val searchResultRepository: SearchResultRepository): ViewModel(){

    /**
     * Get the search results
     */
    fun getSearchResult(searchQuery: String, startIndex: String) = searchResultRepository.getSearchResult(searchQuery,startIndex)

    /**
     * Clear the search results
     */
    fun clearSearchResults() = searchResultRepository.clearSearchResult()


}