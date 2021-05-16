package ru.dimadef.cats.app.data.network.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Cat(
    val id: String,
    val breeds: List<Breed>,
    val url: String,
    val width: Int,
    val height: Int
)