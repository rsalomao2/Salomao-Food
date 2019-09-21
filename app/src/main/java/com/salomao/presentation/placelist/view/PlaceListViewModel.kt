package com.salomao.presentation.placelist.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salomao.data.pojo.Event
import com.salomao.data.pojo.Place
import com.salomao.data.pojo.Status
import com.salomao.data.repository.PlaceRepository
import com.salomao.domain.extention.mutableLiveData
import kotlinx.coroutines.launch

class PlaceListViewModel(
    private val repository: PlaceRepository
) : ViewModel() {

    val onItemClick: (Place) -> (Unit) = {
        currentItemClick.value = Event(it)
    }

    val currentItemClick = MutableLiveData<Event<Place>>()
    val placeList = MutableLiveData<Event<List<Place>>>()
    val navToSecond = MutableLiveData<Event<Boolean>>()
    val errorMessage = MutableLiveData<Event<String>>()
    val showLoading = mutableLiveData(false)
    val showList = mutableLiveData(false)
    val hideKeyBoard = mutableLiveData(false)

    fun loadPlaceList() {
        viewModelScope.launch {
        }
    }

    fun loadPlaceFromQuery(query: String) {
        viewModelScope.launch {
            hideKeyBoard.value = true
            showLoading.value = true
            when (val result = repository.loadPlaceFromQuery(query)) {
                is Status.Success -> {
                    placeList.value = Event(result.response)
                    showList.value = placeList.value?.peekContent()?.isNotEmpty()
                }
                is Status.Error -> {
                    Event(result.responseError)
                    showList.value = false
                }
            }
            showLoading.value = false
        }
    }
}
