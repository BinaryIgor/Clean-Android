package com.iprogrammerr.clean.android

class OutcomeFactory(private val errorTranslation: (e: Exception) -> String) {

    constructor() : this({ e -> e.message?.let { it } ?: "" })

    fun <T> outcomeOfProcess(process: () -> T) = try {
        Outcome.success(process())
    } catch (e: Exception) {
        Outcome.failure<T>(errorTranslation(e))
    }
}