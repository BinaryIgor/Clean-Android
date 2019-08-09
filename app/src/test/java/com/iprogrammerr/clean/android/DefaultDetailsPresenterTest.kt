package com.iprogrammerr.clean.android

import com.iprogrammerr.clean.android.example.DefaultDetailsPresenter
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.Test

class DefaultDetailsPresenterTest {

    @Test
    fun `details are refreshed`() {
        val presenter = DefaultDetailsPresenter()

        var first = ""
        presenter.getDetails { first = it.value() }

        presenter.refresh()

        presenter.getDetails { second ->
            MatcherAssert.assertThat(first, Matchers.not(Matchers.equalTo(second.value())))
        }
    }
}