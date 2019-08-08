package com.iprogrammerr.clean.android

interface Callback<T> {
    fun call(outcome: Outcome<T>)
}