package com.iprogrammerr.clean.android.example

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.iprogrammerr.clean.android.LifecycleCallback
import com.iprogrammerr.clean.android.R

class MainFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        PresentersFactory.newMainPresenter(this).getMainMessage(LifecycleCallback(this) {
            if (it.isSuccess) {
                view.findViewById<TextView>(R.id.message).text = it.value
            }
        })
        view.findViewById<Button>(R.id.details).setOnClickListener { activity.replace(DetailsFragment(), true) }
        return view
    }
}