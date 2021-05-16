package ru.dimadef.cats.app.di

import dagger.Component
import ru.dimadef.cats.app.App
import ru.dimadef.cats.app.features.activities.MainActivity
import ru.dimadef.cats.app.di.modules.*
import ru.dimadef.cats.app.features.favorites.FavoritesFragment
import ru.dimadef.cats.app.features.home.HomeFragment
import ru.dimadef.cats.app.features.main.MainFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    DomainModule::class,
    NetworkModule::class,
    DataSourceModule::class,
    NavigationModule::class,
    RepositoryModule::class])

interface AppComponent {
    fun inject(app: App)

    fun inject(activity: MainActivity)

    fun inject(fragment: MainFragment)
    fun inject(fragment: HomeFragment)
    fun inject(fragment: FavoritesFragment)

}