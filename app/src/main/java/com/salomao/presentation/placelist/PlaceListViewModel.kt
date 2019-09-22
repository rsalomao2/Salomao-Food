package com.salomao.presentation.placelist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.salomao.data.pojo.Event
import com.salomao.data.pojo.Place
import com.salomao.data.pojo.Status
import com.salomao.data.repository.PlaceRepository
import com.salomao.domain.extention.mutableLiveData
import com.salomao.domain.usecase.GPSUseCase
import kotlinx.coroutines.launch

class PlaceListViewModel(
    private val repository: PlaceRepository,
    private val gpsUseCase: GPSUseCase
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
    val isGpsDenied = MutableLiveData<Event<Boolean>>()

    fun loadPlaceFromGpsLocation() {
        viewModelScope.launch {
            showLoading()
            when (val result = gpsUseCase.getUserLocation()) {
                is Status.Success -> loadPlacesFromLatLng(LatLng(result.response.latitude, result.response.longitude))
                is Status.Error -> isGpsDenied.value = Event(true)
            }
            hideLoading()
        }
    }

    private fun loadPlacesFromLatLng(latLng: LatLng) {
        viewModelScope.launch {
            showLoading()
            when (val result = repository.loadPlaceFromLocation(latLng)){
                is Status.Success ->  loadListOnMustable(result)
                is Status.Error -> errorMessage.value = Event(result.responseError)
            }
            hideLoading()
        }
    }

    fun loadPlacesFromQuery(query: String) {
        viewModelScope.launch {
            hideKeyBoard.value = true
            showLoading()
            when (val result = repository.loadPlaceFromQuery(query)) {
                is Status.Success -> {
                    loadListOnMustable(result)
                }
                is Status.Error -> {
                    Event(result.responseError)
                    showList.value = false
                }
            }
            hideLoading()
        }
    }

    private fun hideLoading() {
        showLoading.value = false
    }

    private fun showLoading() {
        showLoading.value = true
    }

    private fun loadListOnMustable(result: Status.Success<List<Place>>) {
        placeList.value = Event(result.response)
        showList.value = placeList.value?.peekContent()?.isNotEmpty()
    }
}
