package com.salomao.presentation.placelist.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.salomao.data.pojo.Event

class PlaceListViewModel: ViewModel() {

    val navToSecond = MutableLiveData<Event<Boolean>>()

    fun onClick(){
        navToSecond.value = Event(true)
    }

}