package com.cesoft.githubviewer

import android.app.Application

//TODO:
// PAGINATION https://blog.hipolabs.com/api-search-with-pagination-for-android-development-in-2020-f47717fb34ac
// PAGINATION https://proandroiddev.com/8-steps-to-implement-paging-library-in-android-d02500f7fffe
// DI: Koin: Is necessary?
// DB: Room: Storing las search strings? Autocomplete?
////////////////////////////////////////////////////////////////////////////////////////////////////
//
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        private lateinit var instance: App
        fun getInstance() = instance
    }
}