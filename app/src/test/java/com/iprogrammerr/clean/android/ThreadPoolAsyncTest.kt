package com.iprogrammerr.clean.android

import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.Test
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class ThreadPoolAsyncTest {

    @Test
    fun `returns an outcome on a given thread`() {
        val executor = Executors.newSingleThreadExecutor()
        var threadId: Long? = null
        val async = ThreadPoolAsync(executor, OutcomeFactory()) { f ->
            val thread = Thread(f)
            threadId = thread.id
            thread.start()
        }
        async.execute({ "" }, { MatcherAssert.assertThat(Thread.currentThread().id, Matchers.equalTo(threadId)) })
        executor.awaitTermination(1, TimeUnit.SECONDS)
    }
}