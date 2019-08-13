package com.iprogrammerr.clean.android

class OutcomeFactory(private val exceptionTranslation: (e: Exception) -> String) {

    constructor() : this({ e -> e.message?.let { it } ?: "" })

    fun <T> ofFunction(function: () -> T) = try {
        Outcome.success(function())
    } catch (e: Exception) {
        Outcome.failure<T>(exceptionTranslation(e))
    }
}