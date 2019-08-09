package com.iprogrammerr.clean.android.example

import com.iprogrammerr.clean.android.Async
import com.iprogrammerr.clean.android.Outcome

class DefaultDetailsPresenter(private val async: Async) : DetailsPresenter {

    private var details: String? = null

    private fun generateDetails() {
        details = "Details[id=${System.currentTimeMillis() % 1000}]"
    }

    override fun refresh() {
        details = null
    }

    override fun getDetails(waitingMsg: String, callback: (Outcome<String>) -> Unit) {
        if (details == null) {
            callback(Outcome.success(waitingMsg))
            async.execute({
                Thread.sleep(100 + (2_000 * Math.random()).toLong())
                generateDetails()
                details!!
            }, callback)
        } else {
            callback(Outcome.success(details!!))
        }
    }
}