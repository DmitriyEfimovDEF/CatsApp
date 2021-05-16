package ru.dimadef.cats.app.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Single
import ru.dimadef.cats.app.data.db.enteties.CatFavoriteEntity

@Dao
interface CatFavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRx(items: CatFavoriteEntity): Completable

    @Query("SELECT * FROM cat_favorites ORDER BY CASE WHEN :isAsc = 1 THEN id END ASC, CASE WHEN :isAsc = 0 THEN id END DESC LIMIT :limit OFFSET :offset")
    fun getPage(limit: Int, offset: Int, isAsc: Boolean = false): Single<List<CatFavoriteEntity>>

    @Query("DELETE FROM cat_favorites WHERE cat_id = :catId")
    fun deleteByCatId(catId: Int): Completable
}