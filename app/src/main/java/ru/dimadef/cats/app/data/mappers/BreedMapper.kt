package ru.dimadef.cats.app.data.mappers

import ru.dimadef.cats.app.data.db.enteties.BreedEntity
import ru.dimadef.cats.app.data.models.BreedData
import ru.dimadef.cats.app.data.network.dto.Breed

fun List<Breed>.toEntity() = map { it.toEntity() }

fun Breed.toEntity() =
    BreedEntity(labelId = id, name = name, origin = origin, wikipediaUrl = wikipediaUrl)

fun List<BreedEntity>.toData() = map { it.toData() }

fun BreedEntity.toData() = BreedData(id, labelId, name, origin, wikipediaUrl)