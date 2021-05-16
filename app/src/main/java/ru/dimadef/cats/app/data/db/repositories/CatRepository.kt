package ru.dimadef.cats.app.data.db.repositories

import io.reactivex.Completable
import io.reactivex.Observable
import ru.dimadef.cats.app.data.Data
import ru.dimadef.cats.app.data.State
import ru.dimadef.cats.app.data.db.AppDatabase
import ru.dimadef.cats.app.data.db.enteties.CatFavoriteEntity
import ru.dimadef.cats.app.data.domain.DomainEvents
import ru.dimadef.cats.app.data.mappers.toData
import ru.dimadef.cats.app.data.mappers.toEntity
import ru.dimadef.cats.app.data.models.CatData
import ru.dimadef.cats.app.data.models.Page
import ru.dimadef.cats.app.data.network.Api
import javax.inject.Inject

interface CatRepository {
    fun loadCats(page: Page, orderBy: String = "ASC"): Observable<Data<List<CatData>>>
    fun loadFavoriteCats(page: Page, orderBy: String = "ASC"): Observable<Data<List<CatData>>>

    fun addFavorite(catId: Int): Completable
    fun removeFavorite(catId: Int): Completable
}

class CatRepositoryImpl @Inject constructor(
    private val db: AppDatabase,
    private val api: Api,
    private val domainEvents: DomainEvents
) : CatRepository {

    override fun loadCats(page: Page, orderBy: String): Observable<Data<List<CatData>>> {
        return Observable.concatEager(
            mutableListOf(
                getCachedCats(page = page, orderBy = orderBy),
                getNetworkCats(page = page, orderBy = orderBy)
            )
        ).scan { previous: Data<List<CatData>>, current: Data<List<CatData>> ->
            if (current.state is State.Error) {
                current.copy(value = previous.value)
            } else {
                current
            }
        }
    }

    private fun getCachedCats(
        page: Page,
        state: State = State.Loading,
        orderBy: String
    ): Observable<Data<List<CatData>>> {
        val isAsc = orderBy == "ASC"
        return db.catDao.getPage(limit = page.limit, offset = page.offset, isAsc = isAsc)
            .map { it.toData() }
            .map { Data(it, state) }
            .toObservable()
    }

    private fun getNetworkCats(page: Page, orderBy: String): Observable<Data<List<CatData>>> {
        return api.loadCats(page = page.currentNum(), limit = page.limit, order = orderBy)
            .map { list -> list.toEntity() }
            .flatMapObservable { entities ->
                db.catDao.insertRx(entities)
                    .andThen(getCachedCats(page = page, state = State.Success, orderBy = orderBy))
            }.onErrorReturn {
                Data(listOf(), State.Error(it))
            }
    }

    override fun loadFavoriteCats(page: Page, orderBy: String): Observable<Data<List<CatData>>> =
        db.catFavoriteDao.getPage(
            limit = page.limit,
            offset = page.offset,
            isAsc = orderBy == "ASC"
        )
            .map { it.map { it.catId } }
            .flatMap { db.catDao.getCatsByIds(it) }
            .map { it.toData() }
            .map { Data(it, State.Success) }
            .toObservable()

    override fun addFavorite(catId: Int): Completable {
        val entity = CatFavoriteEntity(catId = catId)
        return db.catFavoriteDao.insertRx(entity).doOnComplete { notifyFavoritesUpdated() }
    }

    override fun removeFavorite(catId: Int): Completable =
        db.catFavoriteDao.deleteByCatId(catId).doOnComplete { notifyFavoritesUpdated() }

    private fun notifyFavoritesUpdated() =
        domainEvents.notifyAppEvent(DomainEvents.AppEvent.CAT_FAVORITES_UPDATED)
}