package com.iprogrammerr.mvp.template.example

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager

object Presenters {

    fun <P> of(fragment: Fragment, factory: () -> P) = of(fragment.childFragmentManager, factory)

    private fun <P> of(ownerManager: FragmentManager, factory: () -> P): P {
        val key = PresenterHolder::class.java.simpleName
        val holder = ownerManager.findFragmentByTag(key)
        return if (holder is PresenterHolder<*>) {
            holder.get() as P
        } else {
            val presenter = factory()
            ownerManager.beginTransaction().add(PresenterHolder.newInstance(presenter), key).commit()
            presenter
        }
    }

    fun <P> of(activity: FragmentActivity, factory: () -> P) = of(activity.supportFragmentManager, factory)

    class PresenterHolder<Presenter> : Fragment() {

        private var presenter: Presenter? = null

        init {
            retainInstance = true
        }

        companion object {
            fun <Presenter> newInstance(presenter: Presenter): PresenterHolder<Presenter> {
                val holder = PresenterHolder<Presenter>()
                holder.presenter = presenter
                return holder
            }
        }

        fun get() = presenter!!
    }
}