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
     p0.animateCamera(CameraUpdateFactory.newLatLng(LatLng( 30.044281,31.340002)))
  }
}
fun GoogleMap?.mMapAddMarkers(data:ArrayList<CaseModule>){
 for(i in data)
 {
//todo fix null issue
     this?.addMarker(MarkerOptions()
         .icon(BitmapDescriptorFactory.fromResource(R.drawable.map_green_pin))
         .position(LatLng(i.locationLat.safeDouble()?:0.0,i.locationLng.safeDouble()?:0.0)).title(i.title))
 }

}
fun String?.safeDouble():Double?=try{
this?.toDouble()
}catch (e:Exception){null}
