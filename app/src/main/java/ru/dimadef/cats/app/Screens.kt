package ru.dimadef.cats.app

import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.dimadef.cats.app.features.main.MainFragment

object Screens {

    fun MainScreen() = FragmentScreen {
        MainFragment.newInstance()
    }
}