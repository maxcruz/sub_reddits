package com.example.max.redditclient

import android.app.Application
import com.example.data.local.injector.LocalStorageModule
import com.example.data.remote.injector.RedditServiceModule
import com.example.max.redditclient.detail.DetailContract
import com.example.max.redditclient.detail.injector.DaggerDetailComponent
import com.example.max.redditclient.detail.injector.DetailComponent
import com.example.max.redditclient.detail.injector.DetailModule
import com.example.max.redditclient.libraries.images.injector.ImageLoaderModule
import com.example.max.redditclient.list.ListContract
import com.example.max.redditclient.list.injector.DaggerListComponent
import com.example.max.redditclient.list.injector.ListComponent
import com.example.max.redditclient.list.injector.ListModule
import com.raizlabs.android.dbflow.config.FlowConfig
import com.raizlabs.android.dbflow.config.FlowManager


class RedditApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        FlowManager.init(FlowConfig.Builder(this).openDatabasesOnInit(true).build())
    }

    fun getListComponent(view: ListContract.View): ListComponent {
        return DaggerListComponent.builder()
                .imageLoaderModule(ImageLoaderModule(view.getContext()))
                .redditServiceModule(RedditServiceModule())
                .localStorageModule(LocalStorageModule())
                .listModule(ListModule(view))
                .build()
    }

    fun getDetailComponent(view: DetailContract.View): DetailComponent {
        return DaggerDetailComponent.builder()
                .imageLoaderModule(ImageLoaderModule(view.getContext()))
                .localStorageModule(LocalStorageModule())
                .detailModule(DetailModule(view))
                .build()
    }

}