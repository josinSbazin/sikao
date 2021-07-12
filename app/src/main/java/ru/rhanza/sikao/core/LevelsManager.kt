package ru.rhanza.sikao.core

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import ru.rhanza.sikao.levels.cut.Cut
import ru.rhanza.sikao.levels.end.End
import ru.rhanza.sikao.levels.fill.Fill
import ru.rhanza.sikao.levels.run.Run
import ru.rhanza.sikao.levels.shake.Shake
import ru.rhanza.sikao.levels.swipe.Swipe
import ru.rhanza.sikao.levels.tap.Tap

class LevelsManager(activity: Activity) {

    private var prefs: SharedPreferences =
        activity.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private var currentLevel = prefs.getInt(KEY_LEVEL, DEFAULT_LEVEL)
    private var isCleared = false

    fun save() {
        if (!isCleared) {
            prefs.edit(true) {
                putInt(KEY_LEVEL, currentLevel)
            }
        }
    }

    fun current(): Fragment {
        return levels[currentLevel - 1].invoke()
    }

    fun next(): Fragment {
        if (currentLevel < 1 || currentLevel > levels.size) {
            throw IllegalStateException("Current level incorrect -> $currentLevel (levelsSize = ${levels.size}")
        }

        if (currentLevel < levels.size) {
            currentLevel++
        }

        return current()
    }

    fun clear() {
        prefs.edit(true) {
            clear()
        }
        isCleared = true
    }

    companion object {
        private const val PREFS_NAME = "PLAYER_SIKAO"
        private const val KEY_LEVEL = "CURRENT_LEVEL"
        private const val DEFAULT_LEVEL = 1

        private val levels = listOf(
            { Tap.newInstance() },
            { Swipe.newInstance() },
            { Shake.newInstance() },
            { Fill.newInstance() },
            { Run.newInstance() },
            { Cut.newInstance() },
            { End.newInstance() }
        )
    }
}