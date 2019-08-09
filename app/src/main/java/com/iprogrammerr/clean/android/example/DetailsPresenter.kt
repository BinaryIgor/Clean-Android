package com.iprogrammerr.clean.android.example

import com.iprogrammerr.clean.android.Outcome

interface DetailsPresenter {

    fun refresh()

    fun getDetails(waitingMsg: String, callback: (Outcome<String>) -> Unit)
}