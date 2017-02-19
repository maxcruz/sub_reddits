package com.example.max.redditclient.detail

import com.example.domain.interactors.GetSubRedditById

class DetailPresenter(val view: DetailContract.View,
                      val getSubRedditById: GetSubRedditById): DetailContract.Presenter {

    override fun getSubReddit(id: String) {
        val request = GetSubRedditById.Input(id)
        getSubRedditById.execute(request).subscribe {
            view.loadContentData(it.subReddit)
        }
    }

}