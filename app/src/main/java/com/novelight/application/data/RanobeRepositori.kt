package com.novelight.application.data

import android.content.Context
import com.novelight.application.data.entities.RoomBook
import android.util.Log
import com.novelight.application.data.dao.RoomSerieDAO
import com.novelight.application.models.apiModels.ranobeDBModels.RanobeSerieModel
import com.novelight.application.data.service.RanobeService
import com.novelight.application.models.apiModels.ranobeDBModels.RanobeBook
import com.novelight.application.models.apiModels.ranobeDBModels.RanobeGetBook
import com.novelight.application.models.apiModels.ranobeDBModels.RanobeGetSerie
import com.novelight.application.models.apiModels.ranobeDBModels.RanobeRelease
import com.novelight.application.models.apiModels.ranobeDBModels.RanobeReleaseModel
import com.novelight.application.models.apiModels.ranobeDBModels.RanobeReleasesModel
import com.novelight.application.models.apiModels.ranobeDBModels.RanobeGetSeries
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RanobeRepositori {
    companion object {
        private const val baseUrl: String = "https://ranobedb.org/api/v0/"
        private val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        private val service: RanobeService = retrofit.create(RanobeService::class.java)

        fun getSeries(): List<RanobeSerieModel> {
            var series: RanobeGetSeries? = null

            val serieCall: Call<RanobeGetSeries> = service.getSeries()
            val response = serieCall.execute()
            response.body()?.let { series = it }

            return series!!.series
        }

        fun getSerie(id: Int): RanobeSerieModel? {
            var serie: RanobeSerieModel? = null

            val serieCall: Call<RanobeGetSerie> = service.getSerie(id)
            val response = serieCall.execute()
            response.body()?.let { serie = it.series }

            return serie;
        }

        fun getBook(id: Int): RanobeBook? {
            var book: RanobeBook? = null

            val bookCall: Call<RanobeGetBook> = service.getBook(id)
            val response = bookCall.execute()
            response.body()?.let { book = it.book }

            return book
        }


        suspend fun getReleasesForFavorites(context: Context): List<RanobeReleaseModel> {
            val favoriteTitles = RoomRepositori.getFavouriteSeriesTitles(context)
            val allReleases = mutableListOf<RanobeReleaseModel>()

            for (title in favoriteTitles) {
                val response = service.getReleases(title).execute()
                if (response.isSuccessful) {
                    val body = response.body()
                    val releases = body?.releases ?: emptyList()
                    allReleases.addAll(releases)

                }
            }

            return allReleases
        }


    }
}