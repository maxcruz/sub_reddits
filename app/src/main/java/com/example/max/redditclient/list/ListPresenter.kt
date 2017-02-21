package com.example.max.redditclient.list

import com.example.domain.exceptions.OfflineModeException
import com.example.domain.interactors.ListLocalSubReddits
import com.example.domain.interactors.SynchronizeRemoteData
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit

/**
 * The middle man between the view and the domain layer
 *
 * @author Max Cruz
 */
class ListPresenter(val view: ListContract.View,
                    val synchronizeRemoteData: SynchronizeRemoteData,
                    val listLocalSubReddits: ListLocalSubReddits):
        ListContract.Presenter {

    override fun synchronize() {
        view.showProgressIndicator(true)
        synchronizeRemoteData.execute().doFinally { loadSavedEntries().subscribe() }.subscribe(
                { view.showSynchronizedMessage() },
                {
                    when (it) {
                        is UnknownHostException,
                        is SocketTimeoutException,
                        is OfflineModeException -> {
                            view.showOfflineMessage()
                        }
                        else -> {
                            it.printStackTrace()
                        }
                    }
                }
        )
    }

    override fun loadSavedEntries(): Completable {
        view.clearEntries()
        return Completable.create { emitter ->
            listLocalSubReddits.execute().doFinally { view.showProgressIndicator(false) }.subscribe (
                { output -> view.appendEntries(output.list) }
            )
            emitter.onComplete()
        }.delay(1, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread())
    }

}