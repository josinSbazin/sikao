package ru.rhanza.sikao.levels.shake

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.github.tbouron.shakedetector.library.ShakeDetector
import ru.rhanza.sikao.R
import ru.rhanza.sikao.core.router

class Shake : Fragment(R.layout.fragment_shake) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ShakeDetector.create(requireContext()) {
            requireActivity().router.next()
        }
    }

    override fun onResume() {
        super.onResume()
        ShakeDetector.start()
    }

    override fun onPause() {
        super.onPause()
        ShakeDetector.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        ShakeDetector.destroy()
    }

    companion object {
        @JvmStatic
        fun newInstance() = Shake()
    }
}