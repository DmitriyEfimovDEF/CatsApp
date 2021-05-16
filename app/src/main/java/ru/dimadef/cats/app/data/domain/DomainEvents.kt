package ru.dimadef.cats.app.data.domain

import io.reactivex.Observable

interface DomainEvents {
    fun subscribeOnAppEvent(): Observable<AppEvent>
    fun notifyAppEvent(appEvent: AppEvent)

    enum class AppEvent {
        CAT_FAVORITES_UPDATED
    }
}