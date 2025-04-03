package com.novelight.application.data.service

import com.novelight.application.models.apiModels.ranobeDBModels.RanobeReleasesModel
import com.novelight.application.models.apiModels.ranobeDBModels.RanobeSeriesModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RanobeService {
    @GET("series/{id}")
    fun getSerie(@Path("id") id: Int): Call<RanobeSeriesModel>
    @GET("releases?q=&rl=en&sort=Release+date+desc&limit=100&rf=print")
    fun getReleases(): Call<RanobeReleasesModel>
}