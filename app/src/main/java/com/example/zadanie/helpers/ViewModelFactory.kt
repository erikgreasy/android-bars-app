package com.example.zadanie.helpers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.zadanie.data.DataRepository
import com.example.zadanie.ui.viewmodels.AuthViewModel
import com.example.zadanie.ui.viewmodels.BarsViewModel
import com.example.zadanie.ui.viewmodels.DetailViewModel
import com.example.zadanie.ui.viewmodels.LocateViewModel

class ViewModelFactory(private val repository: DataRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AuthViewModel(repository) as T
        }

        if (modelClass.isAssignableFrom(BarsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BarsViewModel(repository) as T
        }

        if (modelClass.isAssignableFrom(LocateViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LocateViewModel(repository) as T
        }

        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DetailViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}