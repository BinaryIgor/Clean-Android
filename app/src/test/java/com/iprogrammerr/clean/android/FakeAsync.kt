package com.iprogrammerr.clean.android

class FakeAsync : Async {

    override fun <T> execute(function: () -> T, callback: (Outcome<T>) -> Unit) {
        val result = try {
            Outcome.success(function())
        } catch (e: Exception) {
            Outcome.failure<T> { e.message?.let { it } ?: "" }
        }
        callback(result)
    }
}