package com.nyayadhish.imagesearch.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nyayadhish.imagesearch.Constants
import com.nyayadhish.imagesearch.debugLog
import com.nyayadhish.imagesearch.retrofit.APIService
import com.nyayadhish.imagesearch.retrofit.CustomisedRetroCallbacks
import retrofit2.Call

class SearchResultRepository private constructor(private val searchResultDao: SearchResultDao) {

    private var mImageCall: Call<SearchImage>? = null
    private var query: String = ""
    private var startIndex = ""

    fun getSearchResult(searchQuery: String, pageNumber: String): LiveData<List<Items>> {
        query = searchQuery
        this.startIndex = pageNumber
        getImages()
        return searchResultDao.getSearchResults()
    }

    /**
     * Update the DAO for search results
     */
    fun updateSearchResult(mList: List<Items>) {
        searchResultDao.updateSearchResults(mList)
    }

    companion object {
        // For Singleton instantiation
        @Volatile
        private var instance: SearchResultRepository? = null

        fun getInstance(searchResultDao: SearchResultDao) =
            instance ?: synchronized(this) {
                instance ?: SearchResultRepository(searchResultDao).also { instance = it }
            }
    }

    /**
     * Retrofit API call for get Images
     */
    private fun getImages(): LiveData<List<Items>> {
        val tempList: MutableLiveData<List<Items>> = MutableLiveData()
        mImageCall = APIService.getBaseUrl().getImages(
            Constants.GOOGLE_KEY
            , Constants.CX
            , query
            , Constants.SEARCH_TYPE
            , startIndex
        )
        mImageCall!!.enqueue(object : CustomisedRetroCallbacks<SearchImage>() {
            override fun hideProgress() {

            }

            override fun showProgress() {

            }

            override fun onSuccess(response: SearchImage?) {
                debugLog(response?.items?.size.toString())
                response?.items?.let {
                    tempList.value = it
                    updateSearchResult(it)
                }
            }

            override fun onError(response: SearchImage?) {

            }

            override fun onFailure(generalErrorMsg: String, systemErrorMsg: String) {

            }

        })
        return tempList
    }

    fun clearSearchResult() {
        searchResultDao.clearSearchResults()
    }


}