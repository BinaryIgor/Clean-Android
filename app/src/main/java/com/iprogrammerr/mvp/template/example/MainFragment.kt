package com.iprogrammerr.mvp.template.example

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.iprogrammerr.mvp.template.BaseFragment
import com.iprogrammerr.mvp.template.LifecycleCallback
import com.iprogrammerr.mvp.template.R
import com.iprogrammerr.mvp.template.ThreadPoolAsync

class MainFragment : BaseFragment<DefaultMainPresenter>({ DefaultMainPresenter(ThreadPoolAsync()) }) {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        getPresenter().getMainMessage(LifecycleCallback(this) {
            if (it.isSuccess()) {
                view.findViewById<TextView>(R.id.message).text = it.value()
            }
        })
        view.findViewById<Button>(R.id.details).setOnClickListener { activity.replace(DetailsFragment(), true) }
        return view
    }
}