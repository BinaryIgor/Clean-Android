package com.iprogrammerr.clean.android.example

import android.content.Context
import android.support.v4.app.Fragment

abstract class BaseFragment : Fragment() {

    protected lateinit var activity: BaseActivity

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is BaseActivity) {
            activity = context
        }
    }
}