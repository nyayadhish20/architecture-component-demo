package com.nyayadhish.imagesearch.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*


object DataTypeConverter {
    private val gson = Gson()
    @TypeConverter
    fun stringToList(data: String?): List<Items> {
        if (data == null) {
            return Collections.emptyList()
        }

        val listType = object : TypeToken<List<Items>>() {

        }.type

        return gson.fromJson<List<Items>>(data, listType)
    }

    @TypeConverter
    fun ListToString(someObjects: List<Items>): String {
        return gson.toJson(someObjects)
    }
}