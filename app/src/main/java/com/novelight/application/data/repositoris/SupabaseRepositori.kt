package com.novelight.application.data.repositoris

import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.auth.status.SessionSource
import io.github.jan.supabase.auth.status.SessionStatus
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import kotlinx.serialization.json.internal.decodeToSequenceByReader

class SupabaseRepositori {
    companion object {
        private val supabase = createSupabaseClient(
            supabaseUrl = "https://bpqbslncjcldezxjjbmf.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImJwcWJzbG5jamNsZGV6eGpqYm1mIiwicm9sZSI6ImFub24iLCJpYXQiOjE3Mzk0MzA5MDgsImV4cCI6MjA1NTAwNjkwOH0.5UNb-lxMxTgeyvuIQpm9dD82P7-h35SScdX-TFe2diY"
        ) {
            install(Auth)
            install(Postgrest)
        }

        suspend fun createUser(mail: String, passwd: String) {
            supabase.auth.signUpWith(Email) {
                email = mail
                password = passwd
            }
        }

        suspend fun logIn(mail: String, passwd: String) {
            supabase.auth.signInWith(Email) {
                email = mail
                password = passwd
            }
        }

        suspend fun checkEmailVerification(): Boolean {
            var verified: Boolean = false

            supabase.auth.sessionStatus.collect {
                when(it) {
                    is SessionStatus.Authenticated -> {
                        println("Received new authenticated session.")
                        verified = true
                        /*
                        when(it.source) { //Check the source of the session
                            SessionSource.External -> TODO()
                            is SessionSource.Refresh -> TODO()
                            is SessionSource.SignIn -> TODO()
                            is SessionSource.SignUp -> TODO()
                            SessionSource.Storage -> TODO()
                            SessionSource.Unknown -> TODO()
                            is SessionSource.UserChanged -> TODO()
                            is SessionSource.UserIdentitiesChanged -> TODO()
                            else -> TODO()
                        }
                         */
                    }
                    else -> {verified = false}
                }
            }

            return verified
        }

    }
}