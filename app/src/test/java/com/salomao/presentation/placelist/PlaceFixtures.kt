package com.salomao.presentation.placelist

import com.salomao.data.pojo.Address
import com.salomao.data.pojo.Location
import com.salomao.data.pojo.Place
import com.salomao.data.pojo.Status
import com.salomao.domain.provider.TestContextProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
object PlaceFixtures {

    val contextProvider = TestContextProvider()
    val dummyAddress = Address(
        "",
        "",
        0.0,
        0.0
    )
    val dummyPlace = Place(
        0,
        "",
        "",
        0f,
        "",
        0,
        "",
        dummyAddress
    )

    val dummyPlaceList = Status.Success(listOf(dummyPlace, dummyPlace, dummyPlace))
    val dummyLocation = Status.Success(Location(0.0, 0.0))

//    val dummyCities = Status.Success(listOf(dummyCity, dummyCity, dummyCity))
//    val dummyAddress = Address("", "", "", "", "")
//    val dummyAddressStatus = Status.Success(Address("", "", "", "", ""))
//    val dummyAddresses = Status.Success(listOf(dummyAddress))
//    val dummyError = Status.Error("patacoada")
//    val dummySuccess = Status.Success(Unit)
}
