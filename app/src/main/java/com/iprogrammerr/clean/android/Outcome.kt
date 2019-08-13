package com.iprogrammerr.clean.android

class Outcome<T> private constructor(value: T?, exception: String?) {

    private val _value = value
    val value: T
        get() = _exception?.let { throw Exception("Outcome have a exception = $exception") } ?: _value!!

    private val _exception = exception
    val exception: String
        get() = _exception?.let { it } ?: throw Exception("There is no exception, result has a value: $value")

    val isSuccess: Boolean
        get() = _value != null

    companion object {
        @JvmStatic
        fun <T> success(value: T) = Outcome<T>(value, null)

        @JvmStatic
        fun <T> failure(exception: String) = Outcome<T>(null, exception)

        @JvmStatic
        fun <T> failure(exception: () -> String) = Outcome<T>(null, exception())
    }
}