package com.nyayadhish.imagesearch.data

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import kotlinx.coroutines.coroutineScope

class ImageDataBaseWorker(
                          context: Context,
                          workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result = coroutineScope {
        try {
            applicationContext.assets.open("ImageDB").use { inputStream ->
                JsonReader(inputStream.reader()).use { jsonReader ->
                    val imageType = object : TypeToken<List<Items>>() {}.type
                    val imageList: List<Items> = Gson().fromJson(jsonReader, imageType)

                    val database = DataBase.getInstance(applicationContext)
                    database.searchResultDao.insertAll(imageList)

                    Result.success()
                }
            }
        } catch (ex: Exception) {
            Log.e(TAG, "Error seeding database", ex)
            Result.failure()
        }
    }

    companion object {
        private val TAG = ImageDataBaseWorker::class.java.simpleName
    }
}