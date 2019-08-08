package com.iprogrammerr.mvp.template.example

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.iprogrammerr.mvp.template.BaseFragment
import com.iprogrammerr.mvp.template.LifecycleCallback
import com.iprogrammerr.mvp.template.R

class DetailsFragment : BaseFragment<DetailsPresenter>({ DefaultDetailsPresenter() }) {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_details, container, false)
        getPresenter().getDetails(LifecycleCallback(this) { o ->
            view.findViewById<TextView>(R.id.details).text = o.value()
        })
        return view
    }
}