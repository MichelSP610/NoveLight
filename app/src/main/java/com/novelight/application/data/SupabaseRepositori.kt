package com.novelight.application.data

import android.content.Context
import com.novelight.application.models.apiModels.supabaseModels.SupabaseFavouriteSerie
import com.novelight.application.models.apiModels.supabaseModels.SupabaseReadRelease
import com.novelight.application.utils.CustomUtils
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.runBlocking

class SupabaseRepositori {
    companion object {
        private val supabase = createSupabaseClient(
            supabaseUrl = "https://bpqbslncjcldezxjjbmf.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImJwcWJzbG5jamNsZGV6eGpqYm1mIiwicm9sZSI6ImFub24iLCJpYXQiOjE3Mzk0MzA5MDgsImV4cCI6MjA1NTAwNjkwOH0.5UNb-lxMxTgeyvuIQpm9dD82P7-h35SScdX-TFe2diY"
        ) {
            install(Auth)
            install(Postgrest)
        }

        // If user has to be manually verified by making the user open it's verification link on their email
        // it returns success even if the user already exists in the database
        // and it returns an unusable token
        // it also doesn't send a verification email

        // either we leave it like this or we change it so accounts autoconfirm,
        // if accounts autoconfirm the database returns the error saying the user already exists

        // as of now we're gonna be leaving it like this, we will decide later if we leave it or we change it
        suspend fun createUser(mail: String, passwd: String, context: Context) {
            supabase.auth.signUpWith(Email) {
                email = mail
                password = passwd
            }

            Thread {
                updateData(context)
            }
        }

        suspend fun logIn(mail: String, passwd: String, context: Context) {
            supabase.auth.signInWith(Email) {
                email = mail
                password = passwd
            }

            Thread {
                RoomRepositori.deleteAllReleases(context)
                RoomRepositori.deleteAllBooks(context)
                RoomRepositori.deleteAllSerieStaffCrossRef(context)
                RoomRepositori.deleteAllSeries(context)

                runBlocking {
                    val supabaseSeries: List<SupabaseFavouriteSerie> = getSeries()
                    supabaseSeries.forEach {
                        val ranobeSerie = RanobeRepositori.getSerie(it.serie_id)!!
                        RoomRepositori.addSerie(
                            context,
                            CustomUtils.getRoomSerieFromRanobeSerie(ranobeSerie)
                        )
                        RoomRepositori.updateSerieInLibrary(context, ranobeSerie.id, true)
                    }
                }

                runBlocking {
                    val supabaseReadRelease: List<SupabaseReadRelease> = getReleases()
                    supabaseReadRelease.forEach {
                        val ranobeRelease = RanobeRepositori.getRelease(it.release_id)
                        RoomRepositori.addRelease(
                            context,
                            CustomUtils.getRoomReleaseFromRanobeRelease(
                                ranobeRelease,
                                it.book_id,
                                it.last_page_read
                            )
                        )
                    }
                }
            }.start()
        }

        fun updateData(context: Context) {
            runBlocking {
                removeFavourites()
                removeReleases()
            }

            Thread {
                runBlocking {
                    insertSeries(
                        RoomRepositori.getFavouriteSeries(context)
                            .map { SupabaseFavouriteSerie(it.id) }
                    )
                }
            }.start()

            Thread {
                runBlocking {
                    insertReleases(
                        RoomRepositori.getReadReleases(context)
                            .map {
                                SupabaseReadRelease(
                                    release_id = it.id,
                                    book_id = it.bookId,
                                    last_page_read = it.lastPageRead
                                )
                            }
                    )
                }
            }.start()
        }

        private suspend fun getSeries(): List<SupabaseFavouriteSerie> {
            return supabase.from("FavouriteSerie").select().decodeList<SupabaseFavouriteSerie>()
        }

        private suspend fun getReleases(): List<SupabaseReadRelease> {
            return supabase.from("ReadRelease").select().decodeList<SupabaseReadRelease>()
        }

        private suspend fun removeFavourites() {
            supabase.from("FavouriteSerie").delete()
        }

        private suspend fun removeReleases() {
            supabase.from("ReadReleases").delete()
        }

        private suspend fun insertReleases(releases: List<SupabaseReadRelease>) {
            supabase.from("ReadRelease").insert(releases)

        }

        private suspend fun insertSeries(series: List<SupabaseFavouriteSerie>) {
            supabase.from("FavouriteSerie").insert(series)
        }
    }
}