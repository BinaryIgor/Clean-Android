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

class DetailsFragment : BaseFragment<DetailsPresenter>({ DefaultDetailsPresenter() }), CustomDialogFragment.Listener {

    private lateinit var detailsView: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_details, container, false)
        detailsView = view.findViewById(R.id.details)
        getPresenter().getDetails(LifecycleCallback(this) { o ->
            detailsView.text = o.value()
        })
        view.findViewById<Button>(R.id.refresh).setOnClickListener {
            CustomDialogFragment().show(childFragmentManager, "")
        }
        return view
    }

    override fun onNo() {

    }

    override fun onYes() {
        getPresenter().refresh()
        getPresenter().getDetails(LifecycleCallback(this) {
            detailsView.text = it.value()
        })
    }
}