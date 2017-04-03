package com.example.max.redditclient.libraries.images

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager

class GlideImageLoader(val context: Context): ImageLoader {

    val requestManager: RequestManager = Glide.with(context)

    override fun load(image: ImageView, url: String) {
        requestManager.load(url).into(image)
    }

}