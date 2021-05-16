package ru.dimadef.cats.app.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.dimadef.cats.app.data.db.converters.Converters
import ru.dimadef.cats.app.data.db.dao.CatDao
import ru.dimadef.cats.app.data.db.dao.CatFavoriteDao
import ru.dimadef.cats.app.data.db.enteties.CatEntity
import ru.dimadef.cats.app.data.db.enteties.CatFavoriteEntity

@Database(
    entities = [CatEntity::class,
        CatFavoriteEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract val catDao: CatDao
    abstract val catFavoriteDao: CatFavoriteDao
}