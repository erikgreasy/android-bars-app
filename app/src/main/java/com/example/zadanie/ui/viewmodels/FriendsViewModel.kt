package com.example.zadanie.ui.viewmodels

import androidx.lifecycle.*
import com.example.zadanie.data.DataRepository
import com.example.zadanie.data.db.model.BarItem
import com.example.zadanie.data.db.model.Friend
import com.example.zadanie.helpers.Evento
import kotlinx.coroutines.launch

class FriendsViewModel(private val repository: DataRepository): ViewModel() {
    private val _message = MutableLiveData<Evento<String>>()
    val message: LiveData<Evento<String>>
        get() = _message

    val loading = MutableLiveData(false)

    private val _added = MutableLiveData<Evento<Boolean>>()
    val added: LiveData<Evento<Boolean>>
        get() = _added

    val friends: LiveData<List<Friend>?> =
        liveData {
            loading.postValue(true)
            repository.apiFriendsList { _message.postValue(Evento(it)) }
            loading.postValue(false)
            emitSource(repository.dbFriends())
        }

    fun refreshData(){
        viewModelScope.launch {
            loading.postValue(true)
            repository.apiFriendsList { _message.postValue(Evento(it)) }
            loading.postValue(false)
        }
    }

    fun addFriend(username: String) {
        viewModelScope.launch {
            loading.postValue(true)
            repository.addFriend(
                username,
                { _message.postValue(Evento(it)) },
                {_added.postValue(Evento(it))}
            )
            loading.postValue(false)
        }
    }

    fun show(msg: String){ _message.postValue(Evento(msg))}
} 