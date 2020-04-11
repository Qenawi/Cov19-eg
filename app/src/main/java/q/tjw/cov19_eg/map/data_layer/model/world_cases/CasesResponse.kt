package q.tjw.cov19_eg.map.data_layer.model.world_cases

data class CasesResponse(val countryInfo: List<CountryInfo>?,
                         val country: String?,
                         val cases: String?,
                         val critical: String?,
                         val active: String?,
                         val testsPerOneMillion: String?,
                         val recovered: String?,
                         val tests: String?,
                         val deathsPerOneMillion: String?,
                         val updated: String?,
                         val deaths: String?,
                         val todayCases: String?,
                         val todayDeaths: String?
)