package q.tjw.cov19_eg.map.core.data
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import q.tjw.cov19_eg.map.core.extentions.mMapToJsonElement
import q.tjw.cov19_eg.map.core.extentions.mMapToObject

const val emptyS = ""
const val emptyI = 0
const val emptyB = false
private const val casePercentage = "casePercentage"
private const val caseAddress = "address"
private const val caseName = "title"
private const val date = "time"
private const val isConfirmed = "verified"
private const val lat = "locationLat"
private const val lng = "locationLng"
private const val userId = "uId"
private const val caseGender = "gender"
private const val caseAge = "age"

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
    ,@SerializedName(caseGender) val gender:String?,
    @SerializedName(casePercentage) val percentage:Int?
) : Parcelable {
    constructor() : this(emptyS, emptyS, emptyS, emptyB, emptyS, emptyS, emptyS, emptyI, emptyS, emptyI)
}
fun Map<String, Any>.mToCaseModule(): CaseModule = this.mMapToJsonElement().mMapToObject()?: dummyCaseModule()
fun dummyCaseModule() = CaseModule()