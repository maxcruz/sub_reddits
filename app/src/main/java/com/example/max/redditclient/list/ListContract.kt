package com.example.max.redditclient.list

import android.content.Context
import com.example.domain.models.SubReddit
import io.reactivex.Completable

/**
 * MVP operation for the main list. In this contract is just defined the view and the presenter,
 * the model contract is separated in the domain layer
 *
 * @author Max Cruz
 */
interface ListContract {

    /**
     * Operations that the presenter can invoke from the view
     */
    interface View {

        fun showProgressIndicator(display: Boolean)
        fun showSynchronizedMessage()
        fun showOfflineMessage()
        fun clearEntries()
        fun appendEntries(list: List<SubReddit>)
        fun goToEntryDetail(subRedditId: String)
        fun getContext(): Context

    }

    /**
     * Operations offered to the view to interact with the domain layer
     */
    interface Presenter {

        fun synchronize()
        fun loadSavedEntries(): Completable

    }

}
