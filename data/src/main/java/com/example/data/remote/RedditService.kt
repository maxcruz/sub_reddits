package com.example.data.remote

import com.example.data.dao.RedditsDAO
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET

interface RedditService {

    companion object {
        val BASE_URL = "https://www.reddit.com/"
    }

    @GET("reddits.json")
    fun getReddits() : Observable<Response<RedditsDAO>>

}