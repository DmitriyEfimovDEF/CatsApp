package ru.dimadef.cats.app.features.home

import androidx.recyclerview.widget.DiffUtil
import moxy.viewstate.strategy.alias.AddToEndSingle
import ru.dimadef.cats.app.features.base.BaseView
import ru.dimadef.cats.app.features.home.items.CatItem

@AddToEndSingle
interface HomeView : BaseView {
    fun setData(items: List<CatItem>, result: DiffUtil.DiffResult)
}