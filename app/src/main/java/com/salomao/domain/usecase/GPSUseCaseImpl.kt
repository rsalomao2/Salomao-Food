package com.salomao.domain.usecase

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.core.content.PermissionChecker.PERMISSION_GRANTED
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.salomao.data.pojo.Location
import com.salomao.data.pojo.Status
import com.salomao.domain.extention.asDeferredAsync
import com.salomao.domain.provider.CoroutineContextProvider
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume

/*
* Class developed by Pedro, a friend of my, where I´ve learned a lot
* all rights for him
* */

class GPSUseCaseImpl(private val context: Context,
                     private val contextProvider: CoroutineContextProvider
) : GPSUseCase {

    companion object {
        private const val REQUEST_PERMISSION = "REQUEST_PERMISSION"
        private const val COULD_NOT_GET_LOCATION = "COULD_NOT_GET_LOCATION"
    }

    private val locationObtained = MutableLiveData<Location>()
    private val fusedLocation = LocationServices.getFusedLocationProviderClient(context)

    override suspend fun getUserLocation(): Status<Location> {
        return if (hasLocationPermission())
            when (val status = getUserCurrentLocation()) {
                is Status.Success -> status
                else -> getUserLastKnownLocation()
            }
        else
            Status.Error(REQUEST_PERMISSION)
    }

    private suspend fun getUserLastKnownLocation(): Status<Location> = withContext(contextProvider.IO) {
        try {
            val location = fusedLocation.lastLocation.asDeferredAsync().await()
            Status.Success(Location(location.latitude, location.longitude))
        } catch (securityException: SecurityException) {
            Status.Error(REQUEST_PERMISSION)
        } catch (exception: Exception) {
            Status.Error(COULD_NOT_GET_LOCATION)
        }
    }

    private suspend fun getUserCurrentLocation() = withContext(contextProvider.MAIN) {
        if (!hasLocationPermission()) return@withContext Status.Error(REQUEST_PERMISSION)
        suspendCancellableCoroutine<Status<Location>> { continuation ->
            try {
                val locationCallback = object : LocationCallback() {
                    override fun onLocationResult(locationResult: LocationResult?) {
                        locationResult?.locations?.map {
                            fusedLocation.removeLocationUpdates(this)
                            continuation.resume(Status.Success(Location(it.latitude, it.longitude)))
                        }
                    }
                }

                fusedLocation.requestLocationUpdates(LocationRequest.create().setNumUpdates(1), locationCallback, null)

                continuation.invokeOnCancellation {
                    fusedLocation.removeLocationUpdates(locationCallback)
                }
            } catch (securityException: SecurityException) {
                continuation.resume(Status.Error(REQUEST_PERMISSION))
            } catch (exception: Exception) {
                continuation.resume(Status.Error(COULD_NOT_GET_LOCATION))
            }
        }
    }

    private fun hasLocationPermission(): Boolean {
        return checkSelfPermission(context, ACCESS_FINE_LOCATION) == PERMISSION_GRANTED ||
                checkSelfPermission(context, ACCESS_COARSE_LOCATION) == PERMISSION_GRANTED
    }

    override fun observeUserLocation(updateInterval: Long, timeUnit: TimeUnit): Status<MutableLiveData<Location>> {
        return try {
            fusedLocation.requestLocationUpdates(getLocationRequest(updateInterval, timeUnit), observeLocationCallback, null)
            Status.Success(locationObtained)
        } catch (securityException: SecurityException) {
            Status.Error(REQUEST_PERMISSION)
        } catch (exception: Exception) {
            Status.Error(COULD_NOT_GET_LOCATION)
        }
    }

    override fun stopObservingUserLocation() {
        fusedLocation.removeLocationUpdates(observeLocationCallback)
    }

    private fun getLocationRequest(updateInterval: Long, timeUnit: TimeUnit): LocationRequest {
        return LocationRequest.create().also {
            it.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            it.interval = timeUnit.toMillis(updateInterval)
        }
    }

    private val observeLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            locationResult?.locations?.map {
                locationObtained.postValue(
                        Location(it.latitude, it.longitude)
                )
            }
        }
    }
}
