package ru.dimadef.cats.app.di.modules

import dagger.Module
import dagger.Provides
import ru.dimadef.cats.app.data.domain.AppDomainEvents
import ru.dimadef.cats.app.data.domain.DomainEvents
import javax.inject.Singleton

@Module
class DomainModule {
    private val domainEvents: AppDomainEvents = AppDomainEvents()

    @Provides
    @Singleton
    fun provideDomainEvents(): DomainEvents = domainEvents
}