package ru.dimadef.cats.app

import android.app.Application
import ru.dimadef.cats.app.di.AppComponent
import ru.dimadef.cats.app.di.DaggerAppComponent
import ru.dimadef.cats.app.di.modules.AppModule
import ru.dimadef.cats.app.di.modules.DataSourceModule
import ru.dimadef.cats.app.di.modules.NetworkModule

class App: Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .dataSourceModule(DataSourceModule())
            .networkModule(NetworkModule())
            .build()

        appComponent.inject(this)
    }
}