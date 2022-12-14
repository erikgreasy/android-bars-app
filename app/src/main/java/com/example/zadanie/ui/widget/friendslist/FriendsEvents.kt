package com.example.zadanie.ui.widget.friendslist

import com.example.zadanie.data.db.model.Friend

interface FriendsEvents {
    fun onFriendClick(friend: Friend)
}