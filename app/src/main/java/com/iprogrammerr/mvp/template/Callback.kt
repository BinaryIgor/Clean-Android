package com.iprogrammerr.mvp.template

interface Callback<T> {
    fun call(outcome: Outcome<T>)
}