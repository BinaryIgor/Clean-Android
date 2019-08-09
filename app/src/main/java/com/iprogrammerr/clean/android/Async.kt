package com.iprogrammerr.clean.android

interface Async {
    fun <T> execute(function: () -> T, callback: (Outcome<T>) -> Unit)
}