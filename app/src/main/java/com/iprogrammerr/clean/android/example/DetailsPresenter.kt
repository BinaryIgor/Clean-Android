package com.iprogrammerr.clean.android.example

import com.iprogrammerr.clean.android.Outcome

interface DetailsPresenter {

    fun refresh()

    fun getDetails(callback: (Outcome<String>) -> Unit)
}