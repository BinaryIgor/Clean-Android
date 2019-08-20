package com.iprogrammerr.clean.android.example

import com.iprogrammerr.clean.android.Outcome

interface DetailsPresenter {

    fun getDetails(waitingMsg: String, callback: (Outcome<String>) -> Unit)

    fun refreshClicked(waitingMsg: String, callback: (Outcome<String>) -> Unit)
}