package q.tjw.cov19_eg.map.data_layer.reprository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import q.tjw.cov19_eg.map.data_layer.model.world_cases.CasesResponse
import q.tjw.cov19_eg.utilities.RemoteConfiguration
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class ConnectionApi @Inject constructor(private val apiServices: ClientApiServices){
    private var url: String = ""
    private val gson: Gson? = null


    fun getCases(): LiveData<ArrayList<CasesResponse>>{

        var data: MutableLiveData<ArrayList<CasesResponse>> = MutableLiveData()
        url = RemoteConfiguration.BASE_URL + RemoteConfiguration.SORTED_CASES

        apiServices.getCases(url).enqueue(object: Callback<ArrayList<CasesResponse>> {
            override fun onResponse(call: Call<ArrayList<CasesResponse>>,
                                    response: Response<ArrayList<CasesResponse>>) {

                Log.v("a7a", response.toString())
                 data.value = response.body()
            }

            override fun onFailure(call: Call<ArrayList<CasesResponse>>, t: Throwable) {
                data.value = null
            }

        })
        return data
    }

}