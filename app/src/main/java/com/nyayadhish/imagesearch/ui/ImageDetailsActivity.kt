package com.nyayadhish.imagesearch.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nyayadhish.imagesearch.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_full_screen_image.*

class ImageDetailsActivity: AppCompatActivity() {

    companion object{
        const val KEY_IMAGE_URL = "url"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_screen_image)

        val url = intent.getStringExtra(KEY_IMAGE_URL)
        Picasso.get().load(url).into(iv_full_size)

    }

}