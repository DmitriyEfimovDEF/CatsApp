package ru.dimadef.cats.app.features.base

import com.github.terrakok.cicerone.Router
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import moxy.MvpPresenter
import moxy.MvpView
import org.jetbrains.annotations.NotNull
import javax.inject.Inject

abstract class BasePresenter<View: MvpView> : MvpPresenter<View>() {

    private var compositeSubscription: CompositeDisposable = CompositeDisposable()

    @Inject
    lateinit var router: Router

    protected fun addSubscription(@NotNull subscription: Disposable){
        compositeSubscription.add(subscription)
    }

    override fun onDestroy() {
        super.onDestroy()

        compositeSubscription.clear()
    }

    open fun onBackPressed() {
        router.exit()
    }
}