package ru.rhanza.sikao.core

import android.util.Log
import android.view.MotionEvent
import android.view.View

class RunTouchListener(private val parameters: Parameters, private val onRunAction: () -> Unit) :
    View.OnTouchListener {

    private var count = 0
    private var startMillis: Long = 0

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        return when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                val time = System.currentTimeMillis()

                val delta = time - startMillis
                Log.d("DELTA", delta.toString())
                if (startMillis == 0L || delta > parameters.deltaTime) {
                    count = 1
                } else {
                    count++
                }
                startMillis = time
                Log.d("DELTA", count.toString())

                if (count == parameters.count) {
                    onRunAction.invoke()
                }
                true
            }
            else -> false
        }
    }

    class Parameters(val count: Int, val deltaTime: Long)
}