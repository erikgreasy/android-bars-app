package com.example.zadanie.ui.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.example.zadanie.R
import com.example.zadanie.data.DataRepository
import com.example.zadanie.data.db.model.BarItem
import com.example.zadanie.helpers.Evento
import kotlinx.coroutines.launch

class BarsViewModel(private val repository: DataRepository): ViewModel() {
    private val _message = MutableLiveData<Evento<String>>()
    val message: LiveData<Evento<String>>
        get() = _message

    val loading = MutableLiveData(false)

    val filters = listOf("Name", "Distance", "People")
    val activeFilter = MutableLiveData(filters.get(0))

    val filtered: MutableLiveData<List<BarItem>?> = MutableLiveData()

    val bars: LiveData<List<BarItem>?> =
          liveData {
              loading.postValue(true)
              val apiBars = repository.apiBarList { _message.postValue(Evento(it)) }
              loading.postValue(false)
              val dbLiveData = repository.dbBars()
              Log.e("ASD", dbLiveData.value.toString())
              emitSource(dbLiveData)
          }

    fun filteredData() {
//        return Transformations.distinctUntilChanged(bars)
    }

    fun setActiveFilter(filterName: String) {
        activeFilter.value = filterName

        filtered.value = (
            bars.value?.sortedWith(
                if(activeFilter.value == "People") {
                    compareByDescending({it.name})
                } else {
                    compareBy({it.name})
                }
            )
        )
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