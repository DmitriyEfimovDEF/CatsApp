package ru.dimadef.cats.app.data.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.dimadef.cats.app.data.db.enteties.BreedEntity

class Converters {

    private val gson: Gson = Gson()

    @TypeConverter
    fun toListBreed(value: String): List<BreedEntity> =
        gson.fromJson(value, object : TypeToken<List<BreedEntity>>() {}.type)

    @TypeConverter
    fun fromListBrees(value: List<BreedEntity>): String = gson.toJson(value)
}