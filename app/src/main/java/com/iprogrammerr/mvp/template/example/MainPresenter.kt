package com.iprogrammerr.mvp.template.example

import com.iprogrammerr.mvp.template.Callback

interface MainPresenter {
    fun getMainMessage(callback: Callback<String>)
}