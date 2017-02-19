package com.example.max.redditclient.detail.injector

import com.example.data.local.injector.LocalStorageModule
import com.example.max.redditclient.detail.DetailActivity
import com.example.max.redditclient.libraries.images.injector.ImageLoaderModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ImageLoaderModule::class, LocalStorageModule::class,
        DetailModule::class))
interface DetailComponent {

    fun inject(activity: DetailActivity)

}