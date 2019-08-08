package com.iprogrammerr.mvp.template.example

import com.iprogrammerr.mvp.template.Callback
import com.iprogrammerr.mvp.template.Outcome

class DefaultDetailsPresenter : DetailsPresenter {

    private var details: String? = null

    private fun generateDetails() {
        details = "Details[id=${System.currentTimeMillis() % 1000}]"
    }

    override fun refresh() {
        details = null
    }

    override fun getDetails(callback: Callback<String>) {
        if (details == null) {
            generateDetails()
        }
        callback.call(Outcome.success(details!!))
    }
}