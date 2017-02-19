package com.example.max.redditclient.detail.injector

import com.example.data.DetailRepository
import com.example.data.local.LocalStorage
import com.example.domain.interactors.GetSubRedditById
import com.example.domain.repository.DetailContractModel
import com.example.max.redditclient.detail.DetailContract
import com.example.max.redditclient.detail.DetailPresenter
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton

@Module
class DetailModule(val view: DetailContract.View) {

    @Singleton
    @Provides
    fun providesDetailContractModel(localStorage: LocalStorage): DetailContractModel {
        return DetailRepository(localStorage)
    }

    @Singleton
    @Provides
    fun providesGetSubRedditById(detailContractModel: DetailContractModel): GetSubRedditById {
        val subscribeOn = Schedulers.io()
        val observeOn = AndroidSchedulers.mainThread()
        return GetSubRedditById(detailContractModel, subscribeOn, observeOn)
    }

    @Singleton
    @Provides
    fun providesDetailPresenter(getSubRedditById: GetSubRedditById): DetailContract.Presenter {
        return DetailPresenter(view, getSubRedditById)
    }

}