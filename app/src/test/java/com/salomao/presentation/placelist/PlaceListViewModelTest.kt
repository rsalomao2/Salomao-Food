package com.salomao.presentation.placelist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer
import com.google.android.gms.maps.model.LatLng
import com.nhaarman.mockitokotlin2.*
import com.salomao.data.pojo.Event
import com.salomao.data.pojo.Place
import com.salomao.data.repository.PlaceRepository
import com.salomao.domain.usecase.GPSUseCase
import com.salomao.presentation.placelist.PlaceFixtures.contextProvider
import com.salomao.presentation.placelist.PlaceFixtures.dummyLocation
import com.salomao.presentation.placelist.PlaceFixtures.dummyPlaceList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class PlaceListViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var lifecycleOwner: LifecycleOwner
    @Mock
    private lateinit var repository: PlaceRepository
    @Mock
    private lateinit var gpsUseCase: GPSUseCase
    @Mock
    private lateinit var booleanObserver: Observer<Boolean>
    @Mock
    private lateinit var placeListObserver: Observer<Event<List<Place>>>

    private lateinit var viewModel: PlaceListViewModel
    private lateinit var lifecycle: LifecycleRegistry


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        lifecycle = LifecycleRegistry(lifecycleOwner)
        given(lifecycleOwner.lifecycle).willReturn(lifecycle)
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)

        viewModel = PlaceListViewModel(contextProvider, repository, gpsUseCase)
    }

    @Test
    fun `it should init fields properly`() {
        assertNull(viewModel.currentItemClick.value)
        assertNull(viewModel.placeList.value)
        assertNull(viewModel.navToPlaceDetail.value)
        assertNull(viewModel.isGpsDenied.value)
        assertFalse(viewModel.showLoading.value!!)
        assertFalse(viewModel.showList.value!!)
        assertFalse(viewModel.showEmptyCard.value!!)
        assertFalse(viewModel.hideKeyBoard.value!!)
    }

    @Test
    fun loadPlaceFromGpsLocation() = runBlocking {

        whenever(gpsUseCase.getUserLocation()).thenReturn(dummyLocation)
        whenever(repository.loadPlaceFromLocation(LatLng(dummyLocation.response.latitude,dummyLocation.response.longitude))).thenReturn(dummyPlaceList)
        viewModel.placeList.observe(lifecycleOwner, placeListObserver)
        viewModel.showList.observe(lifecycleOwner, booleanObserver)
        viewModel.showEmptyCard.observe(lifecycleOwner, booleanObserver)

        viewModel.loadPlaceFromGpsLocation()

        verify(placeListObserver, times(1)).onChanged(any())
        verify(booleanObserver, times(4)).onChanged(any())//2 on initialization and 1 for showList 1 for EmptyCard
    }

    @Test
    fun loadPlacesFromLatLng() = runBlocking {
        val mockQuery = "mochQuery"
        whenever(repository.loadPlaceFromQuery(mockQuery)).thenReturn(dummyPlaceList)
        viewModel.placeList.observe(lifecycleOwner, placeListObserver)
        viewModel.showList.observe(lifecycleOwner, booleanObserver)
        viewModel.showEmptyCard.observe(lifecycleOwner, booleanObserver)

        viewModel.loadPlacesFromQuery(mockQuery)

        verify(placeListObserver, times(1)).onChanged(any())
        verify(booleanObserver, times(4)).onChanged(any())//2 on initialization and 1 for showList 1 for EmptyCard
    }
}