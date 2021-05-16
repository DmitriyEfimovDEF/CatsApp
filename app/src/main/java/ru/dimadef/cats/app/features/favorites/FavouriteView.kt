package ru.dimadef.cats.app.features.favorites

import androidx.recyclerview.widget.DiffUtil
import moxy.viewstate.strategy.alias.AddToEndSingle
import ru.dimadef.cats.app.features.base.BaseView
import ru.dimadef.cats.app.features.favorites.items.FavoriteCatItem

@AddToEndSingle
interface FavoritesView : BaseView {
    fun setData(items: List<FavoriteCatItem>, result: DiffUtil.DiffResult)
}