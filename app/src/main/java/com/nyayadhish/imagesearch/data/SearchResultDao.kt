package com.nyayadhish.imagesearch.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
class SearchResultDao {

    private val searchResultList = mutableListOf<Items>()
    private val searchResult = MutableLiveData<List<Items>>()

    init {
        searchResult.value = searchResultList
    }

    @Insert
    fun updateSearchResults(mList: List<Items>){
        searchResultList.addAll(mList)
        searchResult.postValue(searchResultList)
    }

    @Query("SELECT * FROM items")
    fun getSearchResults() : LiveData<List<Items>> = searchResult

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(imageList: List<Items>) {


    }

}