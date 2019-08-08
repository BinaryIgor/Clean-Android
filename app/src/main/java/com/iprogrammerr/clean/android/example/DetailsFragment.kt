package com.iprogrammerr.clean.android.example

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.iprogrammerr.clean.android.LifecycleCallback
import com.iprogrammerr.clean.android.Presenters
import com.iprogrammerr.clean.android.R

class DetailsFragment : Fragment(), CustomDialogFragment.Listener {

    private lateinit var detailsView: TextView
    private val presenter by lazy {
        Presenters.of(this) { DefaultDetailsPresenter() }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_details, container, false)
        detailsView = view.findViewById(R.id.details)
        presenter.getDetails(LifecycleCallback(this) { o ->
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
        presenter.refresh()
        presenter.getDetails(LifecycleCallback(this) {
            detailsView.text = it.value()
        })
    }
}