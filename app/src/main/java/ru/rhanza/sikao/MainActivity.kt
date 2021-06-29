package ru.rhanza.sikao

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import ru.rhanza.sikao.core.LevelsManager
import ru.rhanza.sikao.core.Router
import ru.rhanza.sikao.levels.end.End
import ru.rhanza.sikao.levels.swipe.Swipe
import ru.rhanza.sikao.levels.tap.Tap

class MainActivity : AppCompatActivity(), Router {
    private lateinit var levelsManager: LevelsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        levelsManager = LevelsManager(this)

        if (savedInstanceState == null) {
            open(levelsManager.current())
        }
    }

    override fun next() {
        open(levelsManager.next())
    }

    override fun onPause() {
        super.onPause()
        levelsManager.save()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (BuildConfig.DEBUG) {
            menuInflater.inflate(R.menu.debug_menu, menu)
            menu?.findItem(R.id.action_reset)?.setOnMenuItemClickListener {
                levelsManager.clear()
                true
            }
        }
        return true
    }

    private fun open(fragment: Fragment) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.container, fragment)
        }
    }
}