package com.iprogrammerr.clean.android

import com.iprogrammerr.clean.android.example.DefaultDetailsPresenter
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.Test

class DefaultDetailsPresenterTest {

    @Test
    fun `returns waiting message before real one`() {
        val presenter = DefaultDetailsPresenter(FakeAsync())
        val waiting = "waiting"
        var waitingCalled = false
        presenter.getDetails(waiting) {
            if (waitingCalled) {
                MatcherAssert.assertThat(it.value, Matchers.not(Matchers.equalTo(waiting)))
            } else {
                MatcherAssert.assertThat(it.value, Matchers.equalTo(waiting))
                waitingCalled = true
            }
        }
        MatcherAssert.assertThat(waitingCalled, Matchers.equalTo(true))
    }

    @Test
    fun `details are refreshed`() {
        val presenter = DefaultDetailsPresenter(FakeAsync())

        var first = ""
        presenter.getDetails("") { first = it.value }

        presenter.refresh()

        presenter.getDetails("") { second ->
            MatcherAssert.assertThat(first, Matchers.not(Matchers.equalTo(second.value)))
        }
    }
}