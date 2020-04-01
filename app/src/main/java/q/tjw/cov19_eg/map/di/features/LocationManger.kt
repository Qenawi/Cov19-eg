package q.tjw.cov19_eg.map.di.features

import android.annotation.SuppressLint
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import com.google.android.gms.location.FusedLocationProviderClient
import q.tjw.cov19_eg.map.core.exception.Either
import q.tjw.cov19_eg.map.core.exception.Failure
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationManger @Inject constructor(
    private val locationManger: LocationManager,
    private val fusedClient: FusedLocationProviderClient
) {
    fun getLocation(onResult: (Either<Failure, Location>) -> Unit = {}) {
        try {
            fusedClient.lastLocation.addOnSuccessListener { l: Location? ->
                l?.let { loc ->
                    onResult(Either.Right(loc))
                } ?: mGetLiveLocation(onResult)
            }.addOnFailureListener { f ->
                onResult(Either.Left(Failure.GPSError))
            }
        } catch (e: Exception) {
            onResult(Either.Left(Failure.SecurityError))
        }
    }

    private fun mGetLiveLocation(onResult: (Either<Failure, Location>) -> Unit = {}) {
        val locationListener: LocationListener = object : LocationListener {
            override fun onLocationChanged(location: Location?) {
                location?.let { loc ->
                    onResult(Either.Right(loc))
                    locationManger.removeUpdates(this)
                }
            }

            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
            override fun onProviderEnabled(provider: String?) {}
            override fun onProviderDisabled(provider: String?) {}
        }
        try {
            locationManger.let {
                if (it.isProviderEnabled(LocationManager.GPS_PROVIDER) && it.isProviderEnabled(
                        LocationManager.NETWORK_PROVIDER
                    )
                )
                    it.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        50L,
                        0f,
                        locationListener
                    )
                else {
                    onResult(Either.Left(Failure.GPSError))
                }
            }
        } catch (ex: SecurityException) {
            onResult(Either.Left(Failure.SecurityError))
        }
    }

    fun clean() {
        //todo Clean up  resoureces
    }
}