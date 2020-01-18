package com.nyayadhish.imagesearch.retrofit

import com.nyayadhish.imagesearch.Utility
import com.nyayadhish.imagesearch.debugLog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Nikhil Nyayadhish on 03 Jan 2020 at 16:01.
 */
abstract class CustomisedRetroCallbacks<T> : Callback<T> {

    companion object{
        val TAG = CustomisedRetroCallbacks::class.simpleName
    }

    init {
        showProgress()
    }

    /**
     * @param call     Instance of Call interface.
     * @param response Response of the API to determine if it indicates success.
     */
    override fun onResponse(call: Call<T>, response: Response<T>) {
        hideProgress()
        debugLog("URL =>" + call.request().url())
        debugLog(response.body().toString())
        if (response.isSuccessful && response.body() != null) {
            onSuccess(response.body())
        } else
            onFailure(call, Exception())
    }

    abstract fun hideProgress()

    abstract fun showProgress()


    /**
     * @param call      Instance of Call interface.
     * @param throwable Throw an error.
     */
    override fun onFailure(call: Call<T>, throwable: Throwable) {

        debugLog("URL =>" + call.request().url())
        if (!Utility.isNetworkAvailable()) {
            /*if (mContext as EditorBaseActivity? is ActivitySplash)
                onFailure(throwable.message, throwable.localizedMessage)
            else {*/
            /*if (mContext != null)
                (mContext as BaseView).onNoNetworkFoud()*/
            onFailure("Please check your internet connection!", throwable.message ?: "")

        } else {
            /*if (mContext != null)
                (mContext as BaseView).onServerError()*/
            onFailure("We could not connect to our servers!", throwable.message ?: "")
        }
    }

    /**
     * @param response If not null and status is equal to 1 then sends response to the activity.
     */
    protected abstract fun onSuccess(response: T?)

    /**
     * @param response If not null and status is equal to 0 then sends response to the activity.
     */
    protected abstract fun onError(response: T?)

    /**
     * @param generalErrorMsg User defined error message.
     * @param systemErrorMsg  System defined error message.
     */
    protected abstract fun onFailure(generalErrorMsg: String, systemErrorMsg: String)
}