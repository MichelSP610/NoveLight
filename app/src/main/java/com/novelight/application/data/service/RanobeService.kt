package com.novelight.application.data.service

import com.novelight.application.models.apiModels.ranobeDBModels.RanobeGetBook
import com.novelight.application.models.apiModels.ranobeDBModels.RanobeGetSerie
import com.novelight.application.models.apiModels.ranobeDBModels.RanobeReleasesModel
import com.novelight.application.models.apiModels.ranobeDBModels.RanobeGetSeries
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RanobeService {
    @GET("series?sort=Relevance+desc&rl=en&limit=99")
    fun getSeries(): Call<RanobeGetSeries>

    @GET("series/{id}")
    fun getSerie(@Path("id") id: Int): Call<RanobeGetSerie>

    @GET("book/{id}")
    fun getBook(@Path("id") id: Int): Call<RanobeGetBook>

    @GET("series")
    fun searchSeriesByTitle(
        @Query("q") title: String,
        @Query("sort") sort: String = "Relevance desc",
        @Query("rl") rl: String = "en",
        @Query("limit") limit: Int = 25
    ): Call<RanobeGetSeries>



//    @GET("releases?q=Sword%20Art%20Online%20Progressive&rl=en&sort=Release+date+desc&limit=25&rf=print")
//    fun getReleases(): Call<RanobeReleasesModel>

    @GET("releases")
    fun getReleases(
        @Query("q") title: String,
        @Query("rl") rl: String = "en",
        @Query("sort") sort: String = "Release date desc",
        @Query("limit") limit: Int = 25,
        @Query("rf") rf: String = "print"
    ): Call<RanobeReleasesModel>
}