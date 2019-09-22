package com.salomao.presentation.placedetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salomao.data.pojo.Dish
import com.salomao.data.pojo.Event
import com.salomao.data.repository.PlaceRepository
import kotlinx.coroutines.launch

class PlaceDetailViewModel(
    repository: PlaceRepository
) : ViewModel(){
    val currentItemClick = MutableLiveData<Event<Dish>>()
    val onItemClick:(Dish) -> (Unit) = {
        currentItemClick.value = Event(it)
    }

    fun loadDishMenuList(placeId: Int){
        viewModelScope.launch {

        }
    }
}