package ru.dimadef.cats.app.utils.diff

import com.mikepenz.fastadapter.IItem
import com.mikepenz.fastadapter.diff.DiffCallback

internal class YDiffCallbackImpl<Item : IItem<*>> : DiffCallback<Item> {
    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.identifier == newItem.identifier
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item) = oldItem == newItem

    override fun getChangePayload(
        oldItem: Item,
        oldItemPosition: Int,
        newItem: Item,
        newItemPosition: Int
    ): Any? =
        null
}