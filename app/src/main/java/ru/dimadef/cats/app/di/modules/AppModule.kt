package ru.dimadef.cats.app.di.modules

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import org.jetbrains.annotations.NotNull
import javax.inject.Singleton

@Module
class AppModule(@NotNull private val app: Application) {
    @Provides
    @Singleton
    internal fun provideApplication(): Application {
        return app
    }

    @Provides
    @Singleton
    internal fun provideContext(): Context {
        return app
    }
}