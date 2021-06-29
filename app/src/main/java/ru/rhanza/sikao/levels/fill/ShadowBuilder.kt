package ru.rhanza.sikao.levels.fill

import android.graphics.Canvas
import android.graphics.Point
import android.util.Log
import android.view.View

class ShadowBuilder(v: View) : View.DragShadowBuilder(v) {
    var angle: Float = 0f

    override fun onProvideShadowMetrics(outShadowSize: Point?, outShadowTouchPoint: Point?) {
        super.onProvideShadowMetrics(outShadowSize, outShadowTouchPoint)
        Log.d("ShadowBuilder", outShadowSize.toString() + " == " + outShadowTouchPoint.toString())
    }

    override fun onDrawShadow(canvas: Canvas) {
        canvas.rotate(angle)
        view.draw(canvas)
    }
}