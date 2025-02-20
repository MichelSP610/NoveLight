package com.novelight.application.fragments.config.sign

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import co.touchlab.kermit.Logger
import com.novelight.application.R
import com.novelight.application.databinding.FragmentLoginBinding
import com.novelight.application.utils.CustomUtils
import com.novelight.application.viewModels.config.sign.SignViewModel
import kotlinx.coroutines.runBlocking

class LoginFragment : Fragment() {
    
    private lateinit var binding: FragmentLoginBinding
    private val signViewModel: SignViewModel by activityViewModels<SignViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater)

        binding.loginButton.setOnClickListener {
            runBlocking {
                login()
            }
        }

        return binding.root
    }

    private suspend fun login() {
        val email = binding.inputUser.text.toString()
        val password = binding.inputPassword.text.toString()

        try {
            checkFieldsNotNull()
            CustomUtils.checkEmailFormat(email)

            if (signViewModel.logIn(email, password)) {
                Toast.makeText(context, "User logged in successfullly", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Logger.e(tag = "ERROR", messageString = e.message!!)
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkFieldsNotNull(): Boolean{
        val user = binding.inputUser.text.toString()
        val password = binding.inputPassword.text.toString()

        if (user != "" && password != "") {
            return true;
        }

        throw Exception("Fields must not be blank")
    }
}