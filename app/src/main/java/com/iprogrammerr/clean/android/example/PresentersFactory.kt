package com.iprogrammerr.clean.android.example

import android.support.v4.app.Fragment
import com.iprogrammerr.clean.android.Async
import com.iprogrammerr.clean.android.Presenters

object PresentersFactory {

    private val dependencies = HashMap<Class<*>, Any>()

    fun <T : Any> addDependency(clazz: Class<T>, dependency: T) {
        dependencies[clazz] = dependency
    }

    @Suppress("unchecked_cast")
    private fun <T> getDependency(clazz: Class<T>): T {
        return try {
            dependencies[clazz] as T
        } catch (e: Exception) {
            throw Exception("Needed dependency is not of $clazz type", e)
        }
    }

    fun newMainPresenter(fragment: Fragment) =
        Presenters.of(fragment) { DefaultMainPresenter(getDependency(Async::class.java)) }

    fun newDefaultPresenter(fragment: Fragment) = Presenters.of(fragment) { DefaultDetailsPresenter() }
}