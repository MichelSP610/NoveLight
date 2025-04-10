package com.novelight.application.data

import com.novelight.application.data.entities.RoomBook
import com.novelight.application.models.apiModels.ranobeDBModels.RanobeSerieModel
import com.novelight.application.data.service.RanobeService
import com.novelight.application.models.apiModels.ranobeDBModels.RanobeBook
import com.novelight.application.models.apiModels.ranobeDBModels.RanobeSeriesModel
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

        fun getSeries(ids: List<Int>): List<RanobeSerieModel> {
            val series: MutableList<RanobeSerieModel> = mutableListOf()
            for (id in ids) {
                val serieCall: Call<RanobeSeriesModel> = service.getSerie(id)
                val response = serieCall.execute()
                response.body()?.let { series.add(it.series) }
            }

            return series
        }

        fun getSerie(id: Int) :RanobeSerieModel? {
            var serie: RanobeSerieModel? = null

            val serieCall: Call<RanobeSeriesModel> = service.getSerie(id)
            val response = serieCall.execute()
            response.body()?.let { serie = it.series }

            return serie;
        }

        fun getBook(id: Int) :RanobeBook? {
            var book: RanobeBook? = null

            val bookCall: Call<RanobeBook> = service.getBook(id)
            val response = bookCall.execute()
            response.body()?.let { book = it }

            return book
        }
    }
}