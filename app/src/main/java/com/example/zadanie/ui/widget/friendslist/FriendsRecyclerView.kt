package com.example.zadanie.ui.widget.friendslist

import android.content.Context
import android.util.AttributeSet
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zadanie.data.db.model.Friend
import com.example.zadanie.ui.fragments.BarDetailFragmentDirections
import com.example.zadanie.ui.fragments.BarsFragmentDirections
import com.example.zadanie.ui.fragments.FriendsFragmentDirections

class FriendsRecyclerView : RecyclerView {
    private lateinit var friendsAdapter: FriendsAdapter
    /**
     * Default constructor
     *
     * @param context context for the activity
     */
    constructor(context: Context) : super(context) {
        init(context)
    }

    /**
     * Constructor for XML layout
     *
     * @param context activity context
     * @param attrs   xml attributes
     */
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    private fun init(context: Context) {
        setHasFixedSize(true)
        layoutManager = LinearLayoutManager(context, VERTICAL, false)
        friendsAdapter = FriendsAdapter(object : FriendsEvents {
            override fun onFriendClick(friend: Friend) {
                if(friend.barId.isNullOrBlank()) {
                    return
                }

                this@FriendsRecyclerView.findNavController().navigate(
                    FriendsFragmentDirections.actionFriendsFragmentToDetailFragment(friend.barId)
                )
            }
        })
        adapter = friendsAdapter
    }
}

@BindingAdapter(value = ["friends"])
fun FriendsRecyclerView.applyItems(
    friends: List<Friend>?
) {
    (adapter as FriendsAdapter).items = friends ?: emptyList()
}