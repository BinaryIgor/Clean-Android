package com.iprogrammerr.clean.android.example

import android.os.Bundle
import com.iprogrammerr.clean.android.BaseActivity
import com.iprogrammerr.clean.android.R

class MainActivity : BaseActivity(R.id.fragment) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            replace(MainFragment(), false)
        }
    }
}
