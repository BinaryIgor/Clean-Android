package com.iprogrammerr.clean.android.example

import com.iprogrammerr.clean.android.Outcome

interface MainPresenter {
    fun getMainMessage(callback: (Outcome<String>) -> Unit)
}