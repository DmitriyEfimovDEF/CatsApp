package ru.dimadef.cats.app.data.mappers

import ru.dimadef.cats.app.data.db.enteties.CatEntity
import ru.dimadef.cats.app.features.favorites.items.FavoriteCatItem
import ru.dimadef.cats.app.features.home.items.CatItem
import ru.dimadef.cats.app.data.models.CatData
import ru.dimadef.cats.app.data.network.dto.Cat


fun List<Cat>.toEntity() = map { it.toEntity() }
fun Cat.toEntity() =
    CatEntity(labelId = id, breeds = breeds.toEntity(), url = url, width = width, height = height)

fun List<CatEntity>.toData() = map { it.toData() }
fun CatEntity.toData() = CatData(id, labelId, breeds.toData(), url, width, height)

fun List<CatData>.toCatItem(): List<CatItem> = map { data ->
    val item = CatItem()
    item.withData(data)
}

fun List<CatData>.toFavoriteCatItem(): List<FavoriteCatItem> = map { data ->
    val item = FavoriteCatItem()
    item.withData(data)
}

