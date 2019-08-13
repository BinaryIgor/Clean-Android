package com.iprogrammerr.clean.android

import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.Test

class OutcomeTest {

    @Test
    fun `returns a value`() {
        val value = "abc"
        val outcome = Outcome.success(value)
        MatcherAssert.assertThat(outcome.isSuccess, Matchers.equalTo(true))
        MatcherAssert.assertThat(outcome.value, Matchers.equalTo(value))
    }

    @Test
    fun `returns an exception`() {
        val msg = "It was a terrible failure"
        val outcome = Outcome.failure<String>(msg)
        MatcherAssert.assertThat(outcome.isSuccess, Matchers.equalTo(false))
        MatcherAssert.assertThat(outcome.exception, Matchers.equalTo(msg))
    }
}