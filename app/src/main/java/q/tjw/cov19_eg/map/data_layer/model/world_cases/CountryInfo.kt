package q.tjw.cov19_eg.map.data_layer.model.world_cases

import com.google.gson.annotations.SerializedName

data class CountryInfo(val flag: String?,
                       @SerializedName("_id")val id: String?,
                       val iso2: String?,
                       val iso3: String?,
                       val lat: String?,
                       val long: String?)