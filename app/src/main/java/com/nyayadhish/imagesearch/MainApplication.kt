package com.nyayadhish.imagesearch

import android.app.Application
import android.content.Context

class MainApplication: Application() {

    companion object{
        lateinit var instance: MainApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    /**
     * @return The live application context
     */
    fun getContext(): Context {
        return applicationContext
    }

}
