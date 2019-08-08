package com.iprogrammerr.clean.android

class Outcome<T> private constructor(private val value: T?, private val exception: String?) {

    companion object {

        fun <T> success(value: T) = Outcome<T>(value, null)

        fun <T> failure(exception: String) = Outcome<T>(null, exception)

        fun <T> failure(exception: () -> String) = Outcome<T>(null, exception())
    }

    fun value(): T {
        exception?.let { throw Exception("Outcome have a exception = $exception") }
        return value!!
    }

    fun isSuccess() = value != null

    fun isFailure() = value == null

    fun exception() = exception?.let { it } ?: throw Exception("There is no exception, result has a value: $value")
}