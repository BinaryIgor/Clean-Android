package com.iprogrammerr.mvp.template.example

import com.iprogrammerr.mvp.template.Async
import com.iprogrammerr.mvp.template.Callback
import com.iprogrammerr.mvp.template.Outcome

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