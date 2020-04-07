package q.tjw.cov19_eg.model

import q.tjw.cov19_eg.map.core.data.WorldLifeStateModule
import q.tjw.cov19_eg.map.core.data.emptyS
import q.tjw.cov19_eg.map.core.extentions.mMapToJsonElement
import q.tjw.cov19_eg.map.core.extentions.mMapToObject

data class User(
    var name: String,
    var phone: String,
    var province: String,
    var age: String,
    var gender: String
)
fun Map<String, Any>.mToUserModule(): User = this.mMapToJsonElement().mMapToObject()?: User(
    emptyS, emptyS, emptyS, emptyS, emptyS)
