package com.example.zadanie.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.zadanie.R
import com.example.zadanie.databinding.FragmentLoginBinding
import com.example.zadanie.helpers.Injection
import com.example.zadanie.helpers.PasswordHelper
import com.example.zadanie.helpers.PreferenceData
import com.example.zadanie.ui.viewmodels.AuthViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
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
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val x = PreferenceData.getInstance().getUserItem(requireContext())
        if ((x?.uid ?: "").isNotBlank()) {
            Navigation.findNavController(view).navigate(R.id.action_to_bars)
            return
        } else {
            activity?.findViewById<BottomNavigationView>(R.id.nav_view)?.isVisible = false
        }

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            model = authViewModel
        }.also { bnd ->
            bnd.login.setOnClickListener {
                if (bnd.username.text.toString().isNotBlank() && bnd.password.text.toString().isNotBlank()) {
                    val hashPassword = String(PasswordHelper.hash(bnd.password.text.toString()))

                    //it.findNavController().popBackStack(R.id.bars_fragment,false)
                    authViewModel.login(
                        bnd.username.text.toString(),
                        hashPassword
                    )
                }else {
                    authViewModel.show("Fill in name and password")
                }
            }

            bnd.signup.setOnClickListener {
                it.findNavController().navigate(R.id.action_to_sign_up)
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