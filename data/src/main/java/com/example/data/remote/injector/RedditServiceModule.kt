package com.example.data.remote.injector

import com.example.data.remote.RedditService
import com.example.data.remote.ServiceFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RedditServiceModule {

    @Provides
    @Singleton
    fun providesRedditService(): RedditService {
        return ServiceFactory.createService(RedditService::class.java, RedditService.BASE_URL)
    }

}