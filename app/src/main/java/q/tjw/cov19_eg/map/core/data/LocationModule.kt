package q.tjw.cov19_eg.map.core.data

import android.os.Parcelable
import androidx.annotation.Nullable
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LocationModule (@Nullable val location:LatLng,
                           @Nullable val time:String?):Parcelable