package q.tjw.cov19_eg.map.ui.status

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import q.tjw.cov19_eg.map.data_layer.model.world_cases.CasesResponse
import q.tjw.cov19_eg.map.data_layer.reprository.ConnectionApi

class WorldStatusVM: ViewModel() {
    private var casesResponse: LiveData<ArrayList<CasesResponse>>? = null
    private var connectionApi: ConnectionApi? = null

    fun getCasesResponse(): LiveData<ArrayList<CasesResponse>> {
        if (casesResponse == null) {
            casesResponse = MutableLiveData()
        }
        return casesResponse!!
    }
    fun init(connectionApi: ConnectionApi?) {
        if (connectionApi != null) {
            this.connectionApi = connectionApi
        }
    }

    fun getCases(){
        casesResponse = connectionApi!!.getCases()
    }
}