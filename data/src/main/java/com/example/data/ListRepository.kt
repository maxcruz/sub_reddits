package com.example.data

import com.example.data.local.LocalStorage
import com.example.data.dao.SubRedditDAO
import com.example.data.remote.RedditService
import com.example.domain.exceptions.OfflineModeException
import com.example.domain.exceptions.UnexpectedHttpCodeException
import com.example.domain.models.SubReddit
import com.example.domain.repository.ListContractModel
import io.reactivex.Observable

class ListRepository(val redditService: RedditService, val localStorage: LocalStorage):
        ListContractModel {

    override fun getRemoteEntries(): Observable<List<SubReddit>> {
        return redditService.getReddits().map { response ->
            when (response.code()) {
                200 -> response.body().data.dataList.map { it.subReddit.toSubReddit() }
                503 -> throw OfflineModeException()
                else -> throw UnexpectedHttpCodeException()
            }
        }
    }

    override fun saveToLocalStorage(list: List<SubReddit>) {
        val daoList = list.map(::SubRedditDAO)
        localStorage.saveObjectList(SubRedditDAO::class.java, daoList)
    }

    override fun clearLocalStorage() {
        localStorage.deleteAllObjects(SubRedditDAO::class.java)
    }

    override fun getLocalEntries(): Observable<List<SubReddit>> {
        val list = localStorage.retrieveAllObjects(SubRedditDAO::class.java).map {
            (it as SubRedditDAO).toSubReddit()
        }
        return Observable.just(list)
    }
}