package ru.dimadef.cats.app.features.base

import moxy.MvpView
import moxy.viewstate.strategy.alias.Skip

interface BaseView: MvpView {
    @Skip
    fun showError(error: Throwable)

    @Skip
    fun hideProgress()
}