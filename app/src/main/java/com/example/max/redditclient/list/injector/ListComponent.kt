package com.example.max.redditclient.list.injector

import com.example.data.local.injector.LocalStorageModule
import com.example.data.remote.injector.RedditServiceModule
import com.example.max.redditclient.libraries.images.injector.ImageLoaderModule
import com.example.max.redditclient.list.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ImageLoaderModule::class, RedditServiceModule::class,
        LocalStorageModule::class, ListModule::class))
interface ListComponent {

    fun inject(activity: MainActivity)

}