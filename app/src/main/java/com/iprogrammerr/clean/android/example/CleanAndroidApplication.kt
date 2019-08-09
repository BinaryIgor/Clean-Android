package com.iprogrammerr.clean.android.example

import android.app.Application
import com.iprogrammerr.clean.android.Async
import com.iprogrammerr.clean.android.ThreadPoolAsync

class CleanAndroidApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        val async = ThreadPoolAsync()
        PresentersFactory.addDependency(Async::class.java, async)
    }
}