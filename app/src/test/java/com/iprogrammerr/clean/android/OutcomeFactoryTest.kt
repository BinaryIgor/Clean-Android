package com.iprogrammerr.clean.android

import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.Test

class OutcomeFactoryTest {

    @Test
    fun `creates successful`() {
        val value = "abc"
        val outcome = OutcomeFactory().ofFunction { value }
        MatcherAssert.assertThat(outcome.isSuccess, Matchers.equalTo(true))
        MatcherAssert.assertThat(outcome.value, Matchers.equalTo(value))
    }

    @Test
    fun `creates failure with translation`() {
        val translated = "Translated exception"
        val outcome = OutcomeFactory { translated }.ofFunction { throw Exception() }
        MatcherAssert.assertThat(outcome.isSuccess, Matchers.equalTo(false))
        MatcherAssert.assertThat(outcome.exception, Matchers.equalTo(translated))
    }
}