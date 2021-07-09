package ru.rhanza.sikao.levels.fill

import android.annotation.SuppressLint
import android.content.Context.SENSOR_SERVICE
import android.graphics.Rect
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.hardware.SensorManager.SENSOR_DELAY_UI
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.rhanza.sikao.R
import ru.rhanza.sikao.core.router
import kotlin.math.abs
import kotlin.math.atan2


class Fill : Fragment(R.layout.fragment_fill) {

    private var isDragged = false

    private lateinit var decanter: ImageView
    private lateinit var glass: ImageView

    private lateinit var sensorManager: SensorManager
    private lateinit var accelerometer: Sensor
    private var sensorListener: SensorEventListener? = null

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val container = view.findViewById<View>(R.id.dragContainer)

        glass = view.findViewById<ImageView>(R.id.glass)
        decanter = view.findViewById<ImageView>(R.id.decanter).apply {
            tag = DECANTER_TAG
        }

        sensorManager = requireActivity().getSystemService(SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) ?: run {
            requireActivity().router.next()
            return
        }

        var dX = 0f
        var dY = 0f

        container.setOnTouchListener { _, event ->
            val x = event.x
            val y = event.y

            if (event.actionMasked == MotionEvent.ACTION_UP) {
                isDragged = false
            }

            if (decanter.isClicked(x, y)) {
                when (event.actionMasked) {
                    MotionEvent.ACTION_DOWN -> {
                        dX = decanter.x - x
                        dY = decanter.y - y
                        isDragged = true
                    }
                    MotionEvent.ACTION_MOVE -> {
                        decanter.x = x + dX
                        decanter.y = y + dY
                    }
                    else -> return@setOnTouchListener false
                }
                return@setOnTouchListener true
            }
            false
        }
    }

    override fun onResume() {
        super.onResume()

        sensorListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                if (isDragged) {
                    val xAxis: Float = event.values[0]
                    val yAxis: Float = event.values[1]
                    val angle = atan2(xAxis.toDouble(), yAxis.toDouble()) * 180 / Math.PI
                    decanter.rotation = -angle.toFloat()
                    checkFilled()
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
                //n/a
            }
        }

        sensorManager.registerListener(
            sensorListener,
            accelerometer,
            SENSOR_DELAY_UI
        )
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(sensorListener, accelerometer)
    }

    private fun checkFilled() {
        if (abs(decanter.rotation) > MAX_ANGLE
            && decanter.x < glass.x + glass.width
            && decanter.x > glass.x) {
            playFillAnimation {
                requireActivity().router.next()
            }
        }
    }

    private fun playFillAnimation(after: () -> Unit) {
        lifecycleScope.launch {
            decanter.setImageResource(R.drawable.ic_water_glass_empty)
            glass.setImageResource(R.drawable.ic_water_glass_full)
            delay(BEFORE_FILL_DELAY_MILLIS)
            after.invoke()
        }
    }

    private fun View.isClicked(x: Float, y: Float): Boolean {
        val rect = Rect()
        getHitRect(rect)
        return rect.contains(x.toInt(), y.toInt())
    }

    companion object {
        private const val DECANTER_TAG = "DECANTER"
        private const val MAX_ANGLE = 80f
        private const val BEFORE_FILL_DELAY_MILLIS = 1000L

        fun newInstance() = Fill()
    }
}