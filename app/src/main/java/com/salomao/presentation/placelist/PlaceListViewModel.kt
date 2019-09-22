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
import com.salomao.domain.provider.CoroutineContextProvider
import com.salomao.domain.usecase.GPSUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch

class PlaceListViewModel(
    contextProvider: CoroutineContextProvider,
    private val repository: PlaceRepository,
    private val gpsUseCase: GPSUseCase
) : ViewModel() {

    val onItemClick: (Place) -> (Unit) = {
        currentItemClick.value = Event(it)
    }
    private val supervisorJob = SupervisorJob()
    private val uiScope = CoroutineScope(contextProvider.MAIN + supervisorJob)

    val currentItemClick = MutableLiveData<Event<Place>>()
    val placeList = MutableLiveData<Event<List<Place>>>()
    val navToPlaceDetail = MutableLiveData<Event<Boolean>>()
    val errorMessage = MutableLiveData<Event<String>>()
    val showLoading = mutableLiveData(false)
    val showList = mutableLiveData(false)
    val showEmptyCard = mutableLiveData(false)
    val hideKeyBoard = mutableLiveData(false)
    val isGpsDenied = MutableLiveData<Event<Boolean>>()

    init {
        loadPlaceFromGpsLocation()
    }

    fun loadPlaceFromGpsLocation() {
        uiScope.launch {
            when (val result = gpsUseCase.getUserLocation()) {
                is Status.Success -> loadPlacesFromLatLng(LatLng(result.response.latitude, result.response.longitude))
                is Status.Error -> isGpsDenied.value = Event(true)
            }
        }
    }

    private fun loadPlacesFromLatLng(latLng: LatLng) {
        uiScope.launch {
            showLoading()
            when (val result = repository.loadPlaceFromLocation(latLng)){
                is Status.Success ->  loadListOnMutable(result)
                is Status.Error -> {
                    errorMessage.value = Event(result.responseError)
                    handlePlaceListView()
                }
            }
            hideLoading()
        }
    }

    fun loadPlacesFromQuery(query: String) {
        uiScope.launch {
            hideKeyBoard.value = true
            showLoading()
            when (val result = repository.loadPlaceFromQuery(query)) {
                is Status.Success -> {
                    loadListOnMutable(result)
                }
                is Status.Error -> {
                    Event(result.responseError)
                    handlePlaceListView()
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

    private fun loadListOnMutable(result: Status.Success<List<Place>>) {
        placeList.value = Event(result.response)
        handlePlaceListView()
    }

    private fun handlePlaceListView() {
        showList.value = placeList.value?.peekContent()?.isNotEmpty()
        showEmptyCard.value = placeList.value?.peekContent()?.isNullOrEmpty()
    }

    override fun onCleared() {
        super.onCleared()
        uiScope.coroutineContext.cancelChildren()
    }
}
