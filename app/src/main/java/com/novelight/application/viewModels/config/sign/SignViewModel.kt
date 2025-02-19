package com.novelight.application.viewModels.config.sign

import androidx.lifecycle.ViewModel
import co.touchlab.kermit.Logger
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.exception.AuthRestException
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import kotlinx.coroutines.runBlocking

class SignViewModel: ViewModel() {

    private val supabase = createSupabaseClient(
        supabaseUrl = "https://bpqbslncjcldezxjjbmf.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImJwcWJzbG5jamNsZGV6eGpqYm1mIiwicm9sZSI6ImFub24iLCJpYXQiOjE3Mzk0MzA5MDgsImV4cCI6MjA1NTAwNjkwOH0.5UNb-lxMxTgeyvuIQpm9dD82P7-h35SScdX-TFe2diY"
    ) {
        install(Auth)
        install(Postgrest)
    }

    fun registerUser(mail: String, passwd: String): Boolean {
        runBlocking {
            try {
                val user = supabase.auth.signUpWith(Email) {
                    email = mail
                    password = passwd
                }
                Logger.i(user.toString(), tag = "supabase-user", )
            } catch (error: AuthRestException) {
                when (error.error) {
                    "email_address_invalid"-> {
                        throw Exception("Invalid Email Address")
                    }
                    "weak_password" -> {
                        throw Exception("Password is too weak")
                    }
                    "over_email_send_rate_limit" -> {
                        throw Exception("Too many emails sent. Wait a while before trying again")
                    }
                    else -> {
                        Logger.e(tag = error.error, messageString = error.message.toString())
                        throw Exception("Server Error")
                    }
                }
            }
        }

        return false
    }
}