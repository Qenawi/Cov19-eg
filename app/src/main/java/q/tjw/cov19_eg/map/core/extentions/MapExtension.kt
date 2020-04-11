package q.tjw.cov19_eg.map.core.extentions

import android.app.Activity
import android.graphics.drawable.BitmapDrawable
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import q.tjw.cov19_eg.R
import q.tjw.cov19_eg.map.core.data.CaseModule


fun Activity?.setUpMap(m:GoogleMap?)=this?.run {
 m?.let {p0->
      p0.setMinZoomPreference(4f)
      p0.setMaxZoomPreference(15f)
      p0.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_dark_style))
     p0.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng( 26.820553, 30.802498), 6f))
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
