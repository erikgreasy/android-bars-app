package com.example.zadanie.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.zadanie.R
import com.example.zadanie.databinding.FragmentAddFriendBinding
import com.example.zadanie.helpers.Injection
import com.example.zadanie.ui.viewmodels.AuthViewModel
import com.example.zadanie.ui.viewmodels.FriendsViewModel

class AddFriendFragment : Fragment() {
    private lateinit var binding: FragmentAddFriendBinding
    private lateinit var friendsViewModel: FriendsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        friendsViewModel = ViewModelProvider(
            this,
            Injection.provideViewModelFactory(requireContext())
        ).get(FriendsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddFriendBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            model = friendsViewModel
            lifecycleOwner = viewLifecycleOwner
        }.also { bnd ->
            bnd.back.setOnClickListener {
                findNavController().navigate(
                    AddFriendFragmentDirections.actionAddFriendFragmentToFriendsFragment()
                )
            }

            bnd.submitBtn.setOnClickListener {
                if (binding.friendNameInput.text.toString().isNotBlank()) {
                    friendsViewModel.addFriend(
                        binding.friendNameInput.text.toString()
                    )
                } else {
                    friendsViewModel.show("Fill in friend's name")
                }
            }
        }

        friendsViewModel.added.observe(viewLifecycleOwner) {
            it?.getContentIfNotHandled()?.let {
                if (it) {
                    friendsViewModel.show("Friend has been added.")
                    findNavController().navigate(
                        R.id.action_global_friendsFragment
                    )
                }
            }
        }

    }
}