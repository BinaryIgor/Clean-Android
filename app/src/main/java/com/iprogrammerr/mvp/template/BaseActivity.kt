package com.iprogrammerr.mvp.template

import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity

abstract class BaseActivity(private val fragmentId: Int) : AppCompatActivity() {

    fun replace(fragment: Fragment, toBackStack: Boolean) {
        val tag = fragment.javaClass.simpleName
        val transaction = supportFragmentManager.beginTransaction()
            .replace(fragmentId, fragment, tag)
        if (toBackStack) {
            transaction.addToBackStack(tag)
        }
        transaction.commit()
    }
}