package q.tjw.cov19_eg.map.core.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import q.tjw.cov19_eg.map.core.extentions.mMapToJsonElement
import q.tjw.cov19_eg.map.core.extentions.mMapToObject
import q.tjw.cov19_eg.map.ui.map_activity.debugPrint
import q.tjw.cov19_eg.map.ui.map_activity.printError
import javax.inject.Named

const val emptyS = ""
const val emptyI = 0
const val emptyB = false

private const val caseAddress = "caseAddress"
private const val caseName = "caseName"
private const val date = "date"
private const val isConfirmed = "isConfirmed"
private const val lat = "lat"
private const val lng = "lng"
private const val userId = "userId"
private const val caseGender = "caseGender"
private const val caseAge = "caseAge"


@Parcelize
data class CaseModule(
    @SerializedName(caseAddress) val address: String?
    , @SerializedName(caseName) val title: String,
    @SerializedName(date) val time: String,
    @SerializedName(isConfirmed) val verified: Boolean
    , @SerializedName(lat) val locationLat: String?,
    @SerializedName(lng) val locationLng: String?,
    @SerializedName(userId) val uId: String,
    @SerializedName(caseAge ) val Age:Int?
    ,@SerializedName(caseGender) val Gender:String?
) : Parcelable {
    constructor() : this(emptyS, emptyS, emptyS, emptyB, emptyS, emptyS, emptyS, emptyI, emptyS)
}
fun Map<String, Any>.mToCaseModule(): CaseModule =
    this.mMapToJsonElement().mMapToObject()?: dummyCaseModule()
fun dummyCaseModule() = CaseModule()