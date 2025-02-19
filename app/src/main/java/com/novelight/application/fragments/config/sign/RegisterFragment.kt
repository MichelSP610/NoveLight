package com.novelight.application.fragments.config.sign

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import co.touchlab.kermit.Logger
import com.novelight.application.R
import com.novelight.application.databinding.FragmentRegisterBinding
import com.novelight.application.viewModels.config.sign.SignViewModel

class RegisterFragment : Fragment() {

    private val signViewModel: SignViewModel by activityViewModels<SignViewModel>()
    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater)

        binding.registerButton.setOnClickListener {
            register()
        }

        return binding.root
    }

    private fun register() {
        val user = binding.inputUser.text.toString()
        val password = binding.inputPassword.text.toString()
        val confirmPassword = binding.inputConfirmPassword.text.toString();

        try {
            checkFieldsNotNull()
            checkEmailFormat()
            checkPasswordConfirmation()
            signViewModel.registerUser(user, password)

        } catch (e: Exception) {
            Logger.e(tag = "ERROR", messageString = e.message!!)
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkFieldsNotNull(): Boolean{
        val user = binding.inputUser.text.toString()
        val password = binding.inputPassword.text.toString()
        val confirmPassword = binding.inputConfirmPassword.text.toString();

        if (user != "" && password != "" && confirmPassword != "") {
            return true;
        }

        throw Exception("Fields must not be blank")
    }

    private fun checkEmailFormat(): Boolean {
        val user = binding.inputUser.text.toString()

        val regex = Regex(
            pattern = ".+@[a-zA-z]+\\.[a-zA-Z]+",
            options = setOf(RegexOption.IGNORE_CASE)
        )

        if (regex.matches(user)) {
            return true;
        }

        throw Exception("Email format is not correct")
    }

    private fun checkPasswordConfirmation(): Boolean {
        val password = binding.inputPassword.text.toString()
        val confirmPassword = binding.inputConfirmPassword.text.toString();

        if (password == confirmPassword) {
            return true
        }

        throw Exception("Passwords are not the same")
    }
}