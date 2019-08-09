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
        last?.let { clear() }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        last?.let { clear() }
    }

    private fun clear() {
        owner.lifecycle.removeObserver(this)
        last = null
    }
}