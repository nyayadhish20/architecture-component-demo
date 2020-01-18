
package com.nyayadhish.imagesearch.retrofit
/*
import android.content.com.nyayadhish.imagesearch.data.Context
import android.util.Log
import com.nyayadhish.imagesearch.Utility
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

*/
/**
 * Created by Nikhil Nyayadish on 02 Aug 2019 at 16:35.
 *//*


abstract class CustomRetroCallbacks<T>() : Callback<T> {

    companion object{
        val TAG = CustomRetroCallbacks::class.simpleName
    }

    private var mContext: com.nyayadhish.imagesearch.data.Context? = null

    constructor(mContext: com.nyayadhish.imagesearch.data.Context) : this() {
        this.mContext = mContext
        (mContext as BaseView).showMaterialProgress()
    }


    */
/**
     * @param call     Instance of Call interface.
     * @param response Response of the API to determine if it indicates success.
     *//*

    override fun onResponse(call: Call<T>, response: Response<T>) {
        Log.i(TAG, "URL : " + call.request().url)
        if (mContext != null)
            (mContext as BaseView).hideMaterialProgress()
        if (response.isSuccessful && response.body() != null) {
            Log.i(TAG, "Response: $response")
            if (response.body() is Statuses && (response.body() as Statuses).status == "1") {
                onSuccess(response.body())
            */
/*} else if (response.body() is Statuses && ((response.body() as Statuses).errorCode == "111" || (response.body() as Statuses).errorCode == "99")) {
                if (mContext != null)
                (mContext as BaseView).onForceLogout()*//*

            } else if ((response.body() is Statuses && (response.body() as Statuses).status == "0"))
                onError(response.body())
            else if(response.body() is LinkedInAuth || response.body() is LinkedInProfile )
                onSuccess(response.body())
        } else
            onFailure(call, Exception())
    }


    */
/**
     * @param call      Instance of Call interface.
     * @param throwable Throw an error.
     *//*

    override fun onFailure(call: Call<T>, throwable: Throwable) {
        debugLog("Server Error = " + throwable.message)
        if (mContext != null)
            (mContext as BaseView).hideMaterialProgress()


        if (!Utility.isNetworkAvailable()) {
            */
/*if (mContext as EditorBaseActivity? is ActivitySplash)
                onFailure(throwable.message, throwable.localizedMessage)
            else {*//*

            if (mContext != null)
                (mContext as BaseView).onNoNetworkFoud()
            onFailure("Please check your internet connection!", throwable.message ?: "")

        } else {
            if (mContext != null)
                (mContext as BaseView).onServerError()
            onFailure("We could not connect to our servers!", throwable.message ?: "")
        }
    }

    */
/**
     * @param response If not null and status is equal to 1 then sends response to the activity.
     *//*

    protected abstract fun onSuccess(response: T?)

    */
/**
     * @param response If not null and status is equal to 0 then sends response to the activity.
     *//*

    protected abstract fun onError(response: T?)

    */
/**
     * @param generalErrorMsg User defined error message.
     * @param systemErrorMsg  System defined error message.
     *//*

    protected abstract fun onFailure(generalErrorMsg: String, systemErrorMsg: String)
}
*/
