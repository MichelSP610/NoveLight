package com.novelight.application.fragments.config.sign

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.novelight.application.R
import com.novelight.application.viewModels.config.sign.SignViewModel
import kotlinx.coroutines.runBlocking

class EmailVerificationFragment : Fragment() {

    private val signViewModel: SignViewModel by activityViewModels<SignViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        runBlocking {
            if (signViewModel.checkEmailVerification()) {
                Toast.makeText(context, "User Verified", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Fuck you", Toast.LENGTH_SHORT).show()
            }
        }

        return inflater.inflate(R.layout.fragment_email_verification, container, false)
    }
}