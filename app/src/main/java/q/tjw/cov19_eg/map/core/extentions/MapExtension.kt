package q.tjw.cov19_eg.map.core.extentions

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.drawable.BitmapDrawable
import android.location.LocationManager
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import q.tjw.cov19_eg.R
import q.tjw.cov19_eg.map.core.data.CaseModule


fun Activity?.setUpMap(m: GoogleMap?) = this?.run {
    m?.let { p0 ->
        p0.setMinZoomPreference(2f)
        p0.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_dark_style))
        p0.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(30.044281, 31.340002),7f))
    }
}

fun GoogleMap?.mMapAddMarkers(data: ArrayList<CaseModule>) {
    for (i in data) {
        var drawId: Int = R.drawable.map_green_marker
        if(i.verified) drawId=R.drawable.map_red_marker
        this?.addMarker(
            MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(drawId))
                .position(
                    LatLng(
                        i.locationLat.safeDouble() ?: 0.0,
                        i.locationLng.safeDouble() ?: 0.0
                    )
                ).title(i.title).snippet("${i.gender} - ${i.address}")
        )
    }

}

fun String?.safeDouble(): Double? = try {
    this?.toDouble()
} catch (e: Exception) {
    null
}


fun Context.isGPSEnabled() = (getSystemService(Context.LOCATION_SERVICE) as LocationManager).isProviderEnabled(LocationManager.GPS_PROVIDER)

fun Context.checkLocationPermission(): Boolean =
    this.checkCallingOrSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED

private val onLocationResultStub = { _: LocationResult? -> Unit }

fun locationCallback(
    locationResult: (locationResult: LocationResult?) -> Unit = onLocationResultStub
): LocationCallback {
    return object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            super.onLocationResult(locationResult)
            locationResult(locationResult)
        }
    }
}
