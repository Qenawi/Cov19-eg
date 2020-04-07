package q.tjw.cov19_eg.map.core.extentions
import android.app.Activity
import android.graphics.BitmapFactory
import android.graphics.drawable.Icon
import android.util.Log
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import q.tjw.cov19_eg.R
import q.tjw.cov19_eg.map.core.data.CaseModule
import q.tjw.cov19_eg.map.ui.map_activity.debugPrint
import q.tjw.cov19_eg.map.ui.map_activity.printError

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
     this?.addMarker(MarkerOptions().position(LatLng(i.locationLat?.toDouble()?:0.0,i.locationLng?.toDouble()?:0.0)).title(i.address))
 }

}