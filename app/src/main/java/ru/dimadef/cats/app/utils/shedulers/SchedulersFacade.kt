package ru.dimadef.cats.app.utils.shedulers

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SchedulersFacade @Inject constructor() {

    fun io(): Scheduler = Schedulers.io()

    fun computation(): Scheduler = Schedulers.computation()

    fun ui(): Scheduler = AndroidSchedulers.mainThread()
}