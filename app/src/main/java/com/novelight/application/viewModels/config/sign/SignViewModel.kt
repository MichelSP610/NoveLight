package com.novelight.application.viewModels.config.sign

import androidx.lifecycle.ViewModel
import co.touchlab.kermit.Logger
import com.novelight.application.data.SupabaseRepositori
import io.github.jan.supabase.auth.exception.AuthRestException

class SignViewModel : ViewModel() {

    suspend fun registerUser(mail: String, passwd: String): Boolean {
        try {
            SupabaseRepositori.createUser(mail, passwd)
            return true
        } catch (error: AuthRestException) {
            when (error.error) {
                "email_address_invalid" -> {
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

    suspend fun logIn(mail: String, passwd: String): Boolean {
        try {
            SupabaseRepositori.logIn(mail, passwd)
            return true
        } catch (error: AuthRestException) {
            when (error.error) {
                "email_not_confirmed" -> {
                    throw Exception("Email address not verified")
                }

                "invalid_credentials" -> {
                    throw Exception("Wrong user or password")
                }

                else -> {
                    Logger.e(tag = error.error, messageString = error.message.toString())
                    throw Exception("Server Error")
                }
            }
        }
    }
}