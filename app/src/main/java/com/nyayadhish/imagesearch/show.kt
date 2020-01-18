package com.nyayadhish.imagesearch

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat

/**
 * Extension method to display the view. (visibility = View.VISIBLE)
 */
fun View.show(): View {
    if (visibility != View.VISIBLE) visibility = View.VISIBLE
    return this
}

/**
 * Extension method to hide the view. (visibility = View.INVISIBLE)
 */
fun View.hide(): View {
    if (visibility != View.INVISIBLE) visibility = View.INVISIBLE
    return this
}

/**
 * Extension method to remove the view. (visibility = View.GONE)
 */
fun View.remove(): View {
    if (visibility != View.GONE) visibility = View.GONE
    return this
}

/**
 * Extension method to toggle a view's visibility.
 */
fun View.toggleVisibility(): View {
    visibility = if (visibility == View.VISIBLE) View.GONE else View.VISIBLE
    return this
}

/**
 * Extension method to get color from resources.
 */
fun Context.getColorCompact(@ColorRes id: Int) = ContextCompat.getColor(this, id)

/**
 * Extension method to get drawable from resources.
 */
fun Context.getDrawableCompact(@DrawableRes id: Int) = ContextCompat.getDrawable(this, id)

/**
 * Extension method to start an Activity.
 */
inline fun <reified T : Activity> Context?.startActivity() =
    this?.startActivity(Intent(this, T::class.java))

/**
 * Extension method to start an Activity for result.
 */
inline fun <reified T : Activity> Activity?.startActivityForResult(requestCode: Int) =
    this?.startActivityForResult(Intent(this, T::class.java), requestCode)

/**
 * Extension method to inflate layout for ViewGroup.
 */
fun ViewGroup.inflate(layoutRes: Int): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, false)
}

fun debugLog(msg: String){
    if(BuildConfig.DEBUG)
        Log.i("DebugLog = ",msg)
}
