package q.tjw.cov19_eg.map.core.extentions
import android.app.Activity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.MapStyleOptions
import q.tjw.cov19_eg.R

fun Activity?.setUpMap(m:GoogleMap?)=this?.run {
 m?.let {p0->
      p0.setMinZoomPreference(4f)
     p0.setMaxZoomPreference(15f)
     p0.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_dark_style))
  }
}