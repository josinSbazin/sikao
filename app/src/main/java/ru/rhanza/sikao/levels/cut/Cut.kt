package ru.rhanza.sikao.levels.cut

import android.os.Bundle
import android.view.ContextMenu
import android.view.ContextMenu.ContextMenuInfo
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import ru.rhanza.sikao.R
import ru.rhanza.sikao.core.router


class Cut : Fragment(R.layout.fragment_cut) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val image = view.findViewById<ImageView>(R.id.cutImage)

        registerForContextMenu(image)
    }

    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = requireActivity().menuInflater
        inflater.inflate(R.menu.cut_context_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.cut -> {
                requireActivity().router.next()
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = Cut()
    }
}