package com.example.zadanie.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.zadanie.R
import com.example.zadanie.databinding.FragmentSignUpBinding
import com.example.zadanie.helpers.Injection
import com.example.zadanie.helpers.PasswordHelper
import com.example.zadanie.helpers.PreferenceData
import com.example.zadanie.ui.viewmodels.AuthViewModel

class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding
    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        authViewModel = ViewModelProvider(
            this,
            Injection.provideViewModelFactory(requireContext())
        ).get(AuthViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val x = PreferenceData.getInstance().getUserItem(requireContext())
        if ((x?.uid ?: "").isNotBlank()) {
            Navigation.findNavController(view).navigate(R.id.action_to_bars)
            return
        }

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            model = authViewModel
        }.also { bnd ->
            bnd.signup.setOnClickListener {
                if (bnd.username.text.toString().isNotBlank() && bnd.password.text.toString().isNotBlank()
                    && bnd.password.text.toString().compareTo(bnd.repeatPassword.text.toString())==0) {

                    val hashPassword = String(PasswordHelper.hash(bnd.password.text.toString()))

                    authViewModel.signup(
                        bnd.username.text.toString(),
                        hashPassword
                    )
                } else if (bnd.username.text.toString().isBlank() || bnd.password.text.toString().isBlank()){
                    authViewModel.show("Fill in name and password")
                } else {
                    authViewModel.show("Passwords must be same")
                }
            }

            bnd.login.setOnClickListener {
                it.findNavController().navigate(R.id.action_to_login)
            }
        }


        authViewModel.user.observe(viewLifecycleOwner){
            it?.let {
                PreferenceData.getInstance().putUserItem(requireContext(),it)
                Navigation.findNavController(requireView()).navigate(R.id.action_to_bars)
            }
        }

    }
}