package com.example.max.redditclient.list.injector

import com.example.data.ListRepository
import com.example.data.local.LocalStorage
import com.example.data.remote.RedditService
import com.example.domain.interactors.ListLocalSubReddits
import com.example.domain.interactors.SynchronizeRemoteData
import com.example.domain.repository.ListContractModel
import com.example.max.redditclient.libraries.images.ImageLoader
import com.example.max.redditclient.list.ListContract
import com.example.max.redditclient.list.ListPresenter
import com.example.max.redditclient.list.adapters.EntryListAdapter
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Singleton

@Module
class ListModule(val view: ListContract.View) {

    @Provides
    @Singleton
    fun providesEntryListAdapter(imageLoader: ImageLoader): EntryListAdapter {
        return EntryListAdapter(view, LinkedList(), imageLoader)
    }

    @Provides
    @Singleton
    fun providesListModel(redditService: RedditService, localStorage: LocalStorage):
            ListContractModel {
        return ListRepository(redditService, localStorage)
    }

    @Provides
    @Singleton
    fun providesSynchronizeRemoteData(listContractModel: ListContractModel): SynchronizeRemoteData {
        val subscribeOn = Schedulers.io()
        val observeOn = AndroidSchedulers.mainThread()
        return SynchronizeRemoteData(listContractModel, subscribeOn, observeOn)
    }

    @Provides
    @Singleton
    fun providesListLocalSubReddits(listContractModel: ListContractModel): ListLocalSubReddits {
        val subscribeOn = Schedulers.io()
        val observeOn = AndroidSchedulers.mainThread()
        return ListLocalSubReddits(listContractModel, subscribeOn, observeOn)
    }

    @Provides
    @Singleton
    fun providesListPresenter(synchronizeRemoteData: SynchronizeRemoteData,
                              listLocalSubReddits: ListLocalSubReddits): ListContract.Presenter {
        return ListPresenter(view, synchronizeRemoteData, listLocalSubReddits)
    }

}