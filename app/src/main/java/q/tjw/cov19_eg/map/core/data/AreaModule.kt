package q.tjw.cov19_eg.map.core.data

import android.location.Location
import com.google.android.gms.maps.model.LatLng

data class AreaModule(val name: String, val location: LatLng)

fun getAreas(): ArrayList<AreaModule> {
    val ret = ArrayList<AreaModule>()
    //todo fill array list
    return ret
}