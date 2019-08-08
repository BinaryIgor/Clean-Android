package com.iprogrammerr.mvp.template.example

import com.iprogrammerr.mvp.template.Callback

interface DetailsPresenter {

    fun refresh()

    fun getDetails(callback: Callback<String>)
}