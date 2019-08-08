package com.iprogrammerr.clean.android

import android.support.v4.app.Fragment

class PresenterHolder<P> : Fragment() {

    private var presenter: P? = null

    init {
        retainInstance = true
    }

    companion object {
        @JvmStatic
        fun <P> holding(presenter: P): PresenterHolder<P> {
            val holder = PresenterHolder<P>()
            holder.presenter = presenter
            return holder
        }
    }

    fun get() = presenter

    override fun onDestroy() {
        super.onDestroy()
        presenter = null
    }
}