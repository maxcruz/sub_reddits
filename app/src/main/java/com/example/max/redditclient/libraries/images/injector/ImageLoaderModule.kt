package com.example.max.redditclient.libraries.images.injector

import android.content.Context
import com.example.max.redditclient.libraries.images.GlideImageLoader
import com.example.max.redditclient.libraries.images.ImageLoader
import dagger.Module
import dagger.Provides

@Module
class ImageLoaderModule(val context: Context) {

    @Provides
    fun providesImageLoader(): ImageLoader {
        return GlideImageLoader(context)
    }

}