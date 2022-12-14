package com.example.zadanie.ui.widget.friendslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.zadanie.R
import com.example.zadanie.data.db.model.Friend
import com.example.zadanie.helpers.autoNotify
import kotlin.properties.Delegates

class FriendsAdapter(val events: FriendsEvents? = null) :
    RecyclerView.Adapter<FriendsAdapter.FriendViewHolder>() {
    var items: List<Friend> by Delegates.observable(emptyList()) { _, old, new ->
        autoNotify(old, new) { o, n -> o.id.compareTo(n.id) == 0 }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        return FriendViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        holder.bind(items[position], events)
    }

    class FriendViewHolder(
        private val parent: ViewGroup,
        itemView: View = LayoutInflater.from(parent.context).inflate(
            R.layout.friend_item,
            parent,
            false)
    ) : RecyclerView.ViewHolder(itemView){

        fun bind(item: Friend, events: FriendsEvents?) {
            itemView.findViewById<TextView>(R.id.name).text = item.name
            itemView.findViewById<TextView>(R.id.restaurant).text = item.barName.toString()

            itemView.setOnClickListener { events?.onFriendClick(item) }
        }
    }
}