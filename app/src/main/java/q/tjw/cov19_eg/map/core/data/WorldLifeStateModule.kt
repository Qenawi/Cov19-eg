package q.tjw.cov19_eg.map.core.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import q.tjw.cov19_eg.map.core.extentions.mMapToJsonElement
import q.tjw.cov19_eg.map.core.extentions.mMapToObject
import javax.inject.Named
@Parcelize
data class WorldLifeStateModule(
    @Named("death") val deathCount: Int,
    @Named("recovered") val recoveredCount: Int,
    @Named("totalCases") val totalCount: Int
) : Parcelable {
 constructor():this(emptyI, emptyI, emptyI)
}
fun Map<String, Any>.mToWorldLifeStateModule(): WorldLifeStateModule = this.mMapToJsonElement().mMapToObject()?:WorldLifeStateModule()
