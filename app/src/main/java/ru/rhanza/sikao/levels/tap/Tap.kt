package ru.rhanza.sikao.levels.tap

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import ru.rhanza.sikao.R
import ru.rhanza.sikao.core.router

class Tap : Fragment(R.layout.fragment_tap) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.setOnClickListener { requireActivity().router.next() }
    }

    companion object {
        @JvmStatic
        fun newInstance() = Tap()
    }
}