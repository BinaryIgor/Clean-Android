package com.iprogrammerr.mvp.template.example

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.widget.Button
import com.iprogrammerr.mvp.template.R

class CustomDialogFragment : DialogFragment() {

    private lateinit var listener: Listener

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        listener = parentFragment?.let { it as Listener } ?: context as Listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = AlertDialog.Builder(context).create()
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_dialog, null, false)
        view.findViewById<Button>(R.id.no).setOnClickListener {
            dismiss()
            listener.onNo()
        }
        view.findViewById<Button>(R.id.yes).setOnClickListener {
            dismiss()
            listener.onYes()
        }
        dialog.setView(view)
        return dialog
    }

    interface Listener {

        fun onNo()

        fun onYes()
    }
}