package ru.dimadef.cats.app.data.interactors

import io.reactivex.Observable
import ru.dimadef.cats.app.data.Data
import ru.dimadef.cats.app.features.favorites.items.FavoriteCatItem
import ru.dimadef.cats.app.features.home.items.CatItem
import ru.dimadef.cats.app.data.mappers.toCatItem
import ru.dimadef.cats.app.data.mappers.toFavoriteCatItem
import ru.dimadef.cats.app.data.models.CatData
import ru.dimadef.cats.app.data.models.Page
import ru.dimadef.cats.app.data.db.repositories.CatRepository
import javax.inject.Inject

class CatsInteractor @Inject constructor(private val catsRepo: CatRepository) {

    fun loadCats(page: Page): Observable<Data<List<CatItem>>> =
        catsRepo.loadCats(page)
            .scan { data1: Data<List<CatData>>, data2: Data<List<CatData>> ->
                val linked = linkedSetOf<CatData>()
                linked.addAll(data1.value)
                linked.addAll(data2.value)
                data2.copy(value = linked.toList())
            }.map { Data(it.value.toCatItem(), it.state) }

    fun loadFavoriteCats(page: Page): Observable<Data<List<FavoriteCatItem>>> =
        catsRepo.loadFavoriteCats(page)
            .scan { data1: Data<List<CatData>>, data2: Data<List<CatData>> ->
                val linked = linkedSetOf<CatData>()
                linked.addAll(data1.value)
                linked.addAll(data2.value)
                data2.copy(value = linked.toList())
            }.map { Data(it.value.toFavoriteCatItem(), it.state) }

    fun addFavorite(catId: Int): Observable<Unit> =
        catsRepo.addFavorite(catId).andThen(Observable.just(Unit))

    fun removeFavorite(catId: Int): Observable<Unit> =
        catsRepo.removeFavorite(catId).andThen(Observable.just(Unit))
}