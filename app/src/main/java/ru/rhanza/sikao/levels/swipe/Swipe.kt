package ru.rhanza.sikao.levels.swipe

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import ru.rhanza.sikao.R
import ru.rhanza.sikao.core.OnSwipeTouchListener
import ru.rhanza.sikao.core.router

class Swipe : Fragment(R.layout.fragment_swipe) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.setOnTouchListener(object : OnSwipeTouchListener(requireContext()) {
            override fun onSwipeDown() {
                requireActivity().router.next()
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance() = Swipe()
    }
}