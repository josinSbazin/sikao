package ru.rhanza.sikao.levels.run

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import ru.rhanza.sikao.R
import ru.rhanza.sikao.core.RunTouchListener
import ru.rhanza.sikao.core.router

class Run : Fragment(R.layout.fragment_run) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.setOnTouchListener(RunTouchListener(RunTouchListener.Parameters(16, 300)) {
            requireActivity().router.next()
        })
    }

    companion object {
        @JvmStatic
        fun newInstance() = Run()
    }
}