package com.iprogrammerr.mvp.template

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager

object Presenters {

    private val HOLDER_TAG = PresenterHolder::class.java.simpleName

    @JvmStatic
    fun <P : Any> of(fragment: Fragment, factory: () -> P) = of(fragment.childFragmentManager, factory)

    @Suppress("UNCHECKED_CAST")
    private fun <P : Any> of(ownerManager: FragmentManager, factory: () -> P): P {
        val holder = ownerManager.findFragmentByTag(HOLDER_TAG)
        return if (holder is PresenterHolder<*>) {
            holder.get() as P
        } else {
            val presenter = factory()
            ownerManager.beginTransaction().add(PresenterHolder.newInstance(presenter), HOLDER_TAG).commit()
            presenter
        }
    }

    @JvmStatic
    fun <P : Any> of(activity: FragmentActivity, factory: () -> P) = of(activity.supportFragmentManager, factory)
}