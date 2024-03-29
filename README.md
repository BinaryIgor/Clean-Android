# Clean Android
Set of classes for implementing MVP pattern and Clean Architecture on Android. It was created based on growing disappointment from creating Android apps. Its framework make it very hard to come up with any testable architecture. [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) fixed many of this issues, but also created new ones. Its ideas also doesn't fit well with MVP architecture, which I think is the best way to separate Android dependent code from pure jvm classes. Using this approach, we can make our framework dependent views passive and put all of our logic(framework independent) into presenters and models, achieving [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html).
## Presenter
The most important part here are presenters. It's encouraged to have one to one reliationship between them and Activty/Fragment. Presenter is simply a POJO, that should get data from view and be source of it. At the same time, it should know nothing about a view, nor an Android framework. Creating them using Presenters, gives you a guarantee that they will survive configurations changes.
```kotlin
package com.iprogrammerr.clean.android

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager

object Presenters {

    private val HOLDER_TAG = PresenterHolder::class.java.simpleName

    @JvmStatic
    fun <P> of(fragment: Fragment, factory: () -> P) = of(fragment.childFragmentManager, factory)

    @Suppress("unchecked_cast")
    private fun <P> of(ownerManager: FragmentManager, factory: () -> P): P {
        val holder = ownerManager.findFragmentByTag(HOLDER_TAG)
        return if (holder is PresenterHolder<*>) {
            holder.get() as P
        } else {
            val presenter = factory()
            ownerManager.beginTransaction().add(PresenterHolder.holding(presenter), HOLDER_TAG).commit()
            presenter
        }
    }

    @JvmStatic
    fun <P> of(activity: FragmentActivity, factory: () -> P) = of(activity.supportFragmentManager, factory)
}
```
PresenterHolder is simply a Fragment without a view and with retainInstance = true. Exactly the same mechanism is used in android's ViewModels, but its implementation is much more complex. Usage:
```kotlin
package com.iprogrammerr.clean.android.example

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.iprogrammerr.clean.android.LifecycleCallback
import com.iprogrammerr.clean.android.Presenters
import com.iprogrammerr.clean.android.R

class DetailsFragment : Fragment(), CustomDialogFragment.Listener {

    private lateinit var detailsView: TextView
    private val presenter by lazy {
        Presenters.of(this) { DefaultDetailsPresenter() }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_details, container, false)
        detailsView = view.findViewById(R.id.details)
        presenter.getDetails(LifecycleCallback(this) { o ->
            detailsView.text = o.value
        })
        view.findViewById<Button>(R.id.refresh).setOnClickListener {
            CustomDialogFragment().show(childFragmentManager, "")
        }
        return view
    }

    override fun onNo() {

    }

    override fun onYes() {
        presenter.refreshClicked(getString(R.string.waiting), LifecycleCallback(this) {
            detailsView.text = it.value
        })
    }
}
```

## LifecycleCallback
This component show you how simple it is to have lifecycle aware callbacks.
```kotlin
package com.iprogrammerr.clean.android

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent

class LifecycleCallback<T>(private val owner: LifecycleOwner, private val callback: (Outcome<T>) -> Unit) :
    LifecycleObserver, (Outcome<T>) -> Unit {

    private var last: Outcome<T>? = null

    override fun invoke(outcome: Outcome<T>) {
        if (owner.lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
            callback(outcome)
        } else {
            last = outcome
            owner.lifecycle.addObserver(this)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        last?.let {
            callback(it)
            clear()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        last?.let {
            clear()
        }
    }

    private fun clear() {
        last?.let {
            owner.lifecycle.removeObserver(this)
            last = null
        }
    }
}
```
Outcome is another simple class that wraps your model with success/failure state for better View-Presenter communication.
```kotlin
package com.iprogrammerr.clean.android

class Outcome<T> private constructor(value: T?, exception: String?) {

    private val _value = value
    val value: T
        get() = _exception?.let { throw Exception("Outcome have an exception = $it") } ?: _value!!

    private val _exception = exception
    val exception: String
        get() = _exception?.let { it } ?: throw Exception("There is no exception, result has a value: $value")

    val isSuccess: Boolean
        get() = _value != null

    companion object {
        @JvmStatic
        fun <T> success(value: T) = Outcome<T>(value, null)

        @JvmStatic
        fun <T> failure(exception: String) = Outcome<T>(null, exception)

        @JvmStatic
        fun <T> failure(exception: () -> String) = Outcome<T>(null, exception())
    }
}
```
## Testing
Thanks to separation of concerns, testing is a breeze.
```kotlin
package com.iprogrammerr.clean.android.example

import com.iprogrammerr.clean.android.Async
import com.iprogrammerr.clean.android.Outcome

class DefaultMainPresenter(private val async: Async) : MainPresenter {

    private lateinit var message: String

    override fun getMainMessage(callback: (Outcome<String>) -> Unit) {
        if (::message.isInitialized) {
            callback(Outcome.success(message))
        } else {
            async.execute({
                Thread.sleep((Math.random() * 2_000).toLong())
                message = "Hello ${System.currentTimeMillis() / 1_000}"
                message
            }, callback)
        }
    }
}
```
```kotlin
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
```