package q.tjw.cov19_eg.map.core.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import q.tjw.cov19_eg.map.core.extentions.mMapToJsonElement
import q.tjw.cov19_eg.map.core.extentions.mMapToObject
import javax.inject.Named
@Parcelize
data class WorldLifeStateModule(
    @SerializedName("death") val death: Int,
    @SerializedName("recovered") val recovered: Int,
    @SerializedName("totalCases") val totalCases: Int
) : Parcelable {
 constructor():this(emptyI, emptyI, emptyI)
}
fun Map<String, Any>.mToWorldLifeStateModule(): WorldLifeStateModule = this.mMapToJsonElement().mMapToObject()?:WorldLifeStateModule()
