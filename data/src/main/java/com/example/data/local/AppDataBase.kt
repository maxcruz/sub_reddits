package com.example.data.local

import com.raizlabs.android.dbflow.annotation.Database

@Database(name = AppDataBase.NAME, version = AppDataBase.VERSION)
class AppDataBase {

    companion object {
        const val NAME = "RedditDataBase"
        const val VERSION = 1
    }

}