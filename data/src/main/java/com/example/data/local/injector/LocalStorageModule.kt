package com.example.data.local.injector

import com.example.data.local.LocalStorage
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocalStorageModule {

    @Provides
    @Singleton
    fun providesLocalStorage(): LocalStorage {
        return LocalStorage()
    }

}