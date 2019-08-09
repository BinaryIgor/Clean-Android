package com.iprogrammerr.clean.android.example

import com.iprogrammerr.clean.android.Outcome

class DefaultDetailsPresenter : DetailsPresenter {

    private var details: String? = null

    private fun generateDetails() {
        details = "Details[id=${System.currentTimeMillis() % 1000}]"
    }

    override fun refresh() {
        details = null
    }

    override fun getDetails(callback: (Outcome<String>) -> Unit) {
        if (details == null) {
            generateDetails()
        }
        callback(Outcome.success(details!!))
    }
}