package q.tjw.cov19_eg.map.data_layer.reprository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import q.tjw.cov19_eg.map.data_layer.model.world_cases.CasesResponse
import q.tjw.cov19_eg.utilities.RemoteConfiguration
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class ConnectionApi @Inject constructor(private val apiServices: ClientApiServices){
    private var url: String = ""


    fun getCases(): LiveData<CasesResponse>{
        var data: MutableLiveData<CasesResponse> = MutableLiveData<CasesResponse>()
        url = RemoteConfiguration.BASE_URL + RemoteConfiguration.SORTED_CASES

        apiServices.getCases(url).enqueue(object: Callback<CasesResponse> {
            override fun onResponse(call: Call<CasesResponse>, response: Response<CasesResponse>) {
                data.value = response.body()
            }

            override fun onFailure(call: Call<CasesResponse>, t: Throwable) {
                data.value = null
            }

        })
        return data
    }

}