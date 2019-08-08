package com.iprogrammerr.clean.android.example

import com.iprogrammerr.clean.android.Callback

interface MainPresenter {
    fun getMainMessage(callback: Callback<String>)
}