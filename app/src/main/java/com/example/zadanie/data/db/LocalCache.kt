package com.example.zadanie.data.db

import androidx.lifecycle.LiveData
import com.example.zadanie.data.db.model.BarItem

class LocalCache(private val dao: DbDao) {
    suspend fun insertBars(bars: List<BarItem>){
        dao.insertBars(bars)
    }

    suspend fun deleteBars(){ dao.deleteBars() }

    fun getBars(): LiveData<List<BarItem>?> = dao.getBars()
}