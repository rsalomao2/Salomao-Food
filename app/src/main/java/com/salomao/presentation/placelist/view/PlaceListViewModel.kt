package com.salomao.presentation.placelist.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salomao.data.pojo.Address
import com.salomao.data.pojo.Event
import com.salomao.data.pojo.Place
import com.salomao.data.pojo.Status
import kotlinx.coroutines.launch

class PlaceListViewModel : ViewModel() {

    val currentItemClick = MutableLiveData<Event<Place>>()
    val placeList = MutableLiveData<Event<List<Place>>>()
    val onItemClick: (Place) -> (Unit) = {
        currentItemClick.value = Event(it)
    }
    val navToSecond = MutableLiveData<Event<Boolean>>()

    init {
        val dummyList = mutableListOf<Place>()
        repeat(10) {
            dummyList.add(
                Place(
                    "Restaurant name",
                    "data:image/png;base64,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",
                    3.5.toFloat(),
                    "",
                    2,
                    "",
                    Address(
                        "Rua Eurides Cunha, 85",
                        "Curitiba",
                        "Brazil",
                        0.0.toLong(),
                        0.0.toLong()


                    )
                )
            )
        }
        placeList.value = Event(dummyList)
    }

    fun loadPlaceList() {
        viewModelScope.launch {

            //            showLoading.value = false
        }
    }


}