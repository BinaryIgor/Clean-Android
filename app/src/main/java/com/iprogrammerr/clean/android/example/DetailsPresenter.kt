package com.iprogrammerr.clean.android.example

import com.iprogrammerr.clean.android.Callback

interface DetailsPresenter {

    fun refresh()

    fun getDetails(callback: Callback<String>)
}