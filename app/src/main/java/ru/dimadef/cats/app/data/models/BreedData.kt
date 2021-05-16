package ru.dimadef.cats.app.data.models

data class BreedData(
    val id: Int,
    val labelId: String,
    val name: String,
    val origin: String,
    val wikipediaUrl: String
)