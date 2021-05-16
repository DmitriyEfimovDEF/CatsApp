package ru.dimadef.cats.app.data.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import ru.dimadef.cats.app.data.network.dto.Cat

interface Api {
    @GET("images/search")
    fun loadCats(
        @Query("page") page: Int,
        @Query("limit") limit: Int = 25,
        @Query("mime_types") mimeTypes: String = "jpg",
        @Query("size") size: String = "small",
        @Query("order") order: String = "ASC"
    ): Single<List<Cat>>
}