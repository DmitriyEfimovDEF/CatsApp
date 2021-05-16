package ru.dimadef.cats.app.di.modules

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.dimadef.cats.app.data.db.AppDatabase
import javax.inject.Singleton


@Module
class DataSourceModule {
    @Singleton
    @Provides
    fun provideDatabase(context: Context) =
        Room.databaseBuilder(context, AppDatabase::class.java, "catDB").build()
}