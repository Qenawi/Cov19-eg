package q.tjw.cov19_eg.map.data_layer.reprository

import q.tjw.cov19_eg.map.data_layer.model.world_cases.CasesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface ClientApiServices {

    @GET
    fun getCases(@Url url: String?): Call<ArrayList<CasesResponse>>

}