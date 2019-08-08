package com.iprogrammerr.clean.android.example

import com.iprogrammerr.clean.android.Async
import com.iprogrammerr.clean.android.Callback
import com.iprogrammerr.clean.android.Outcome

class DefaultMainPresenter(private val async: Async) : MainPresenter {

    private lateinit var message: String

    override fun getMainMessage(callback: Callback<String>) {
        if (::message.isInitialized) {
            callback.call(Outcome.success(message))
        } else {
            async.execute({
                Thread.sleep((Math.random() * 5_000).toLong())
                message = "Hello ${System.currentTimeMillis()}"
                message
            }, callback)
        }
    }
}