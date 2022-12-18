package com.example.zadanie.ui.viewmodels

import androidx.lifecycle.*
import com.example.zadanie.data.DataRepository
import com.example.zadanie.data.db.model.BarItem
import com.example.zadanie.helpers.Evento
import kotlinx.coroutines.launch

enum class SortKeys {
    PEOPLE,
    NAME,
}

class BarsViewModel(private val repository: DataRepository): ViewModel() {
    private val _message = MutableLiveData<Evento<String>>()
    val message: LiveData<Evento<String>>
        get() = _message

    val loading = MutableLiveData(false)

    val sortKey: MutableLiveData<SortKeys> = MutableLiveData(SortKeys.values().first())

    val bars: LiveData<List<BarItem>?> =
        Transformations.switchMap(sortKey) {
            liveData {
                repository.apiBarList { _message.postValue(Evento(it)) }

                loading.postValue(true)
                loading.postValue(false)
                emitSource(repository.dbBars(it))
            }
        }

    fun refreshData(){
        viewModelScope.launch {
            loading.postValue(true)
            repository.apiBarList { _message.postValue(Evento(it)) }
            loading.postValue(false)
        }
    }

    fun show(msg: String){ _message.postValue(Evento(msg))}
}