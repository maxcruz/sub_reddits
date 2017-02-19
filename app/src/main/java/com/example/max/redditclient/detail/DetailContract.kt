package com.example.max.redditclient.detail

import android.content.Context
import com.example.domain.models.SubReddit

interface DetailContract {

    interface View {
        fun loadContentData(subReddit: SubReddit)
        fun getContext(): Context
    }

    interface Presenter {
        fun getSubReddit(id: String)
    }

}