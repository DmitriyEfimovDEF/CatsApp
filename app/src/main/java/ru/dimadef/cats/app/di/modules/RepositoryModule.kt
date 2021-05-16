package ru.dimadef.cats.app.di.modules

import dagger.Binds
import dagger.Module
import ru.dimadef.cats.app.data.db.repositories.CatRepository
import ru.dimadef.cats.app.data.db.repositories.CatRepositoryImpl

@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindCatRepository(repository: CatRepositoryImpl): CatRepository
}