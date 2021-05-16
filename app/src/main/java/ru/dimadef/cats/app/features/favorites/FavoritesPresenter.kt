package ru.dimadef.cats.app.features.favorites

import io.reactivex.subjects.PublishSubject
import ru.dimadef.cats.app.data.State
import ru.dimadef.cats.app.data.domain.DomainEvents
import ru.dimadef.cats.app.features.base.BasePresenter
import ru.dimadef.cats.app.features.favorites.items.FavoriteCatItem
import ru.dimadef.cats.app.data.interactors.CatsInteractor
import ru.dimadef.cats.app.data.models.Page
import ru.dimadef.cats.app.utils.diff.YFastAdapterDiffUtil
import ru.dimadef.cats.app.utils.shedulers.SchedulersFacade
import javax.inject.Inject

class FavoritesPresenter @Inject constructor(
    private val schedulersFacade: SchedulersFacade,
    private val interactor: CatsInteractor,
    private val domainEvents: DomainEvents
) : BasePresenter<FavoritesView>() {

    private var items = linkedSetOf<FavoriteCatItem>()
    private val page = Page(25)
    private val catsSubject: PublishSubject<Page> = PublishSubject.create()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        addSubscription(domainEvents.subscribeOnAppEvent()
            .subscribe {
                if (it == DomainEvents.AppEvent.CAT_FAVORITES_UPDATED) {
                    loadCurrentPage()
                }
            })

        addSubscription(catsSubject
            .startWith(page)
            .observeOn(schedulersFacade.io())
            .flatMap { newPage ->
                interactor.loadFavoriteCats(newPage)
                    .map {
                        val fullItems = items + it.value
                        Pair(
                            it,
                            YFastAdapterDiffUtil.calculateDiff(
                                items.toList(),
                                fullItems.toList()
                            )
                        )
                    }
            }
            .observeOn(schedulersFacade.ui())
            .subscribe({ pair ->
                val data = pair.first
                val newItems = data.value
                val diffResult = pair.second

                when (data.state) {
                    is State.Success -> {
                        viewState.hideProgress()
                    }
                    is State.Error -> {
                        viewState.hideProgress()
                        viewState.showError(data.state.error)
                    }
                }

                items.addAll(newItems)
                viewState.setData(items = items.toList(), result = diffResult)


            }, {
                viewState.showError(it)
            })
        )
    }

    fun goToNextPage() {
        if (items.size >= page.offset + page.limit) {
            page.next()

            catsSubject.onNext(page)
        } else {
            viewState.hideProgress()
        }
    }

    fun loadCurrentPage() {
        catsSubject.onNext(page)
    }
}