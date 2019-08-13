package com.iprogrammerr.clean.android

import java.util.concurrent.Executor
import java.util.concurrent.Executors

class ThreadPoolAsync(
    private val executor: Executor,
    private val factory: OutcomeFactory,
    private val outcomeThread: (result: () -> Unit) -> Unit
) : Async {

    constructor(factory: OutcomeFactory, outcomeThread: (result: () -> Unit) -> Unit) : this(
        Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2),
        factory,
        outcomeThread
    )

    override fun <T> execute(function: () -> T, callback: (Outcome<T>) -> Unit) {
        executor.execute {
            val outcome = factory.ofFunction(function)
            outcomeThread { callback(outcome) }
        }
    }
}