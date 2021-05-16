package ru.dimadef.cats.app.features.activities

import ru.dimadef.cats.app.Screens
import ru.dimadef.cats.app.features.base.BasePresenter
import javax.inject.Inject


class MainActivityPresenter @Inject constructor(): BasePresenter<MainActivityView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        router.newRootScreen(Screens.MainScreen())
    }

}