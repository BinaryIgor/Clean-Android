package com.iprogrammerr.mvp.template

interface Async {
    fun <T> execute(function: () -> T, callback: Callback<T>)
}