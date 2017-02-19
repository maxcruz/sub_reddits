package com.example.max.redditclient.libraries.images

import android.widget.ImageView

interface ImageLoader {

    fun load(image: ImageView, url: String)

}