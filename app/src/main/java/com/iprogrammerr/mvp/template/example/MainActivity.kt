package com.iprogrammerr.mvp.template.example

import android.os.Bundle
import com.iprogrammerr.mvp.template.BaseActivity
import com.iprogrammerr.mvp.template.R

class MainActivity : BaseActivity(R.id.fragment) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            replace(MainFragment(), false)
        }
    }
}
