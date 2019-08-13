package com.iprogrammerr.clean.android

import android.os.Handler
import android.os.Looper

class HandlerThread(private val handler: Handler) : (() -> Unit) -> Unit {

    constructor() : this(Handler(Looper.getMainLooper()))

    override fun invoke(function: () -> Unit) {
        handler.post(function)
    }
}