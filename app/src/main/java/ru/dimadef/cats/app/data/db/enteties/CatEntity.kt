package ru.dimadef.cats.app.data.db.enteties

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cats")
data class CatEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "label_id") val labelId: String,
    val breeds: List<BreedEntity> = listOf(),
    val url: String,
    val width: Int,
    val height: Int
)