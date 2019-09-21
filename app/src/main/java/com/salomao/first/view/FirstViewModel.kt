package com.salomao.first.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.salomao.pojo.Event

class FirstViewModel: ViewModel() {

    val navToSecond = MutableLiveData<Event<Boolean>>()

    fun onClick(){
        navToSecond.value = Event(true)
    }

}