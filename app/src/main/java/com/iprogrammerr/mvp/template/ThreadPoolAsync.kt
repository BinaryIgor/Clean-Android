package com.iprogrammerr.mvp.template

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class ThreadPoolAsync(private val executor: Executor, private val main: Handler) : Async {

    constructor(executor: Executor) : this(executor, Handler(Looper.getMainLooper()))

    constructor() : this(
        Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2),
        Handler(Looper.getMainLooper())
    )

    override fun <T> execute(function: () -> T, callback: Callback<T>) {
        executor.execute {
            val result = try {
                Outcome.success(function())
            } catch (e: Exception) {
                Outcome.failure<T> { e.message?.let { it } ?: "" }
            }
            main.post { callback.call(result) }
        }
    }
}