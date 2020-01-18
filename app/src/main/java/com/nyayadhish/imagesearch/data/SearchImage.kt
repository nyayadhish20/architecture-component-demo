package com.nyayadhish.imagesearch.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName



data class SearchImage (

    @SerializedName("kind") val kind : String,
    @SerializedName("url") val url : Url,
    @SerializedName("queries") val queries : Queries,
    @SerializedName("context") val context : Context,
    @SerializedName("searchInformation") val searchInformation : SearchInformation,
    @SerializedName("items") val items : List<Items>
)

data class Context (
    @SerializedName("title") val title : String
)
@Entity(tableName = "image")
data class Image (
    @SerializedName("contextLink") val contextLink : String,
    @SerializedName("height") val height : Int,
    @SerializedName("width") val width : Int,
    @SerializedName("byteSize") val byteSize : Int,
    @SerializedName("thumbnailLink") val thumbnailLink : String,
    @SerializedName("thumbnailHeight") val thumbnailHeight : Int,
    @SerializedName("thumbnailWidth") val thumbnailWidth : Int
)


@Entity(tableName = "items")
data class Items (
    @PrimaryKey
    @SerializedName("kind") val kind : String,
    @SerializedName("title") val title : String,
    @SerializedName("htmlTitle") val htmlTitle : String,
    @SerializedName("link") val link : String,
    @SerializedName("displayLink") val displayLink : String,
    @SerializedName("snippet") val snippet : String,
    @SerializedName("htmlSnippet") val htmlSnippet : String,
    @SerializedName("mime") val mime : String,
    @SerializedName("fileFormat") val fileFormat : String,
    @TypeConverters(DataTypeConverter::class)
    @Embedded
    @SerializedName("image") val image : Image
)

data class NextPage (

    @SerializedName("title") val title : String,
    @SerializedName("totalResults") val totalResults : String,
    @SerializedName("searchTerms") val searchTerms : String,
    @SerializedName("count") val count : Int,
    @SerializedName("startIndex") val startIndex : Int,
    @SerializedName("inputEncoding") val inputEncoding : String,
    @SerializedName("outputEncoding") val outputEncoding : String,
    @SerializedName("safe") val safe : String,
    @SerializedName("cx") val cx : String,
    @SerializedName("searchType") val searchType : String
)

data class Queries (

    @SerializedName("request") val request : List<Request>,
    @SerializedName("nextPage") val nextPage : List<NextPage>
)

data class Request (

    @SerializedName("title") val title : String,
    @SerializedName("totalResults") val totalResults : String,
    @SerializedName("searchTerms") val searchTerms : String,
    @SerializedName("count") val count : Int,
    @SerializedName("startIndex") val startIndex : Int,
    @SerializedName("inputEncoding") val inputEncoding : String,
    @SerializedName("outputEncoding") val outputEncoding : String,
    @SerializedName("safe") val safe : String,
    @SerializedName("cx") val cx : String,
    @SerializedName("searchType") val searchType : String
)

data class SearchInformation (

    @SerializedName("searchTime") val searchTime : Double,
    @SerializedName("formattedSearchTime") val formattedSearchTime : Double,
    @SerializedName("totalResults") val totalResults : String,
    @SerializedName("formattedTotalResults") val formattedTotalResults : String
)

data class Url (

    @SerializedName("type") val type : String,
    @SerializedName("template") val template : String
)