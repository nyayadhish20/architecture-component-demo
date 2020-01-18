package com.nyayadhish.imagesearch.retrofit

import com.nyayadhish.imagesearch.Constants
import com.nyayadhish.imagesearch.data.SearchImage
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

/**
 * Created by Nikhil Nyayadhish on 01 Aug 2019 at 11:16.
 */
interface APIService {

    /**
     * Get the instance of Base URL.
     */
    companion object {
        fun getBaseUrl(): APIService {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(
                    GsonConverterFactory.create()
                )
                .baseUrl(Constants.BASE_URL)
                .build()
            return retrofit.create(APIService::class.java)
        }

        /**
         * @return Instance of OkHttpClient class with modified timeout
         */
        private fun getClient(): OkHttpClient {
            val httpTimeout: Long = 20
            val okHttpClientBuilder = OkHttpClient.Builder()
            okHttpClientBuilder.connectTimeout(httpTimeout, TimeUnit.SECONDS)
            okHttpClientBuilder.readTimeout(httpTimeout, TimeUnit.SECONDS)
            return okHttpClientBuilder.build()
        }
    }

    /**
     * GET API
     */

    @GET("v1")
    fun getImages(
        @Query("key") key: String
        , @Query("cx") cx: String
        , @Query("q") searchterm: String
        , @Query("searchType") searchType: String
        , @Query("start") index: String
    ): Call<SearchImage>

}

