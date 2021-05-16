package ru.dimadef.cats.app.data.domain

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class AppDomainEvents : DomainEvents {

    private val appEventSubject: PublishSubject<DomainEvents.AppEvent> = PublishSubject.create()

    override fun subscribeOnAppEvent(): Observable<DomainEvents.AppEvent> = appEventSubject
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    override fun notifyAppEvent(appEvent: DomainEvents.AppEvent) = appEventSubject.onNext(appEvent)
}