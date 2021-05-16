package ru.dimadef.cats.app.data.db.enteties

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class BreedEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "label_id") val labelId: String,
    val name: String,
    val origin: String,
    @ColumnInfo(name = "wikipedia_url") val wikipediaUrl: String
)