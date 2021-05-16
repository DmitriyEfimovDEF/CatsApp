package ru.dimadef.cats.app.data.models


data class CatData(
    val id: Int,
    val labelId: String,
    val breeds: List<BreedData>,
    val url: String,
    val width: Int,
    val height: Int
)