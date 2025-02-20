package com.novelight.application.data.repositoris

import co.touchlab.kermit.Logger
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.auth.status.SessionStatus
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

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

    }
}