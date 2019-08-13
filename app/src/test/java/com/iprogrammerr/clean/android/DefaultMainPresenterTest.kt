package com.iprogrammerr.clean.android

import com.iprogrammerr.clean.android.example.DefaultMainPresenter
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.Test

class DefaultMainPresenterTest {

    @Test
    fun `returns message with a current date`() {
        val presenter = DefaultMainPresenter(FakeAsync())
        presenter.getMainMessage { o ->
            MatcherAssert.assertThat(o.isSuccess, Matchers.equalTo(true))
            MatcherAssert.assertThat(o.value, Matchers.equalTo("Hello ${System.currentTimeMillis() / 1_000}"))
        }
    }

    @Test
    fun `returned message is cached`() {
        val presenter = DefaultMainPresenter(FakeAsync())

        var first = ""
        presenter.getMainMessage { first = it.value }

        var second = " "
        presenter.getMainMessage { second = it.value }

        MatcherAssert.assertThat(first, Matchers.equalTo(second))
    }
}
