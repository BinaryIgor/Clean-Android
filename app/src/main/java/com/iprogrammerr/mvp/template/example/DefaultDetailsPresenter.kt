package com.iprogrammerr.mvp.template.example

import com.iprogrammerr.mvp.template.Callback
import com.iprogrammerr.mvp.template.Outcome

class DefaultDetailsPresenter : DetailsPresenter {

    private val details = "Details[id=${System.currentTimeMillis() % 1000}]"

    override fun getDetails(callback: Callback<String>) {
        callback.call(Outcome.success(details))
    }
}