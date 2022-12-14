package com.example.zadanie.data.db

import androidx.lifecycle.LiveData
import com.example.zadanie.data.db.model.BarItem
import com.example.zadanie.data.db.model.Friend

class LocalCache(private val dao: DbDao) {
    /**
     * Bars
     */
    suspend fun insertBars(bars: List<BarItem>){
        dao.insertBars(bars)
    }

    suspend fun deleteBars(){ dao.deleteBars() }

    fun getBars(): LiveData<List<BarItem>?> = dao.getBars()

    /**
     * Friends
     */
    suspend fun insertFriends(friends: List<Friend>){
        dao.insertFriends(friends)
    }

    suspend fun deleteFriends() { dao.deleteFriends() }

    fun getFriends(): LiveData<List<Friend>?> = dao.getFriends()
}