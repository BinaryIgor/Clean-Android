package com.iprogrammerr.mvp.template

import android.support.v4.app.Fragment

class PresenterHolder<P : Any> : Fragment() {

    private lateinit var presenter: P

    init {
        retainInstance = true
    }

    companion object {
        fun <P : Any> newInstance(presenter: P): PresenterHolder<P> {
            val holder = PresenterHolder<P>()
            holder.presenter = presenter
            return holder
        }
    }

    fun get() = presenter
}