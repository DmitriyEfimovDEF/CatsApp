package ru.dimadef.cats.app.data.network.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Breed(
    val id: String,
    val name: String,
    val origin: String,
    @Json(name = "wikipedia_url") val wikipediaUrl: String
)