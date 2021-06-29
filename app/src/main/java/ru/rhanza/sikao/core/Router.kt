package ru.rhanza.sikao.core

import android.app.Activity

interface Router {
    fun next()
}

val Activity.router: Router get() = this as Router