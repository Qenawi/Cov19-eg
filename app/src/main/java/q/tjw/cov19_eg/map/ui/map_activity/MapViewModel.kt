package q.tjw.cov19_eg.map.ui.map_activity

import android.app.Application
import androidx.lifecycle.MutableLiveData
import q.tjw.cov19_eg.map.core.base.BaseViewModel
import q.tjw.cov19_eg.map.core.data.CaseModule
import q.tjw.cov19_eg.map.core.data.WorldLifeStateModule
import q.tjw.cov19_eg.map.core.exception.Failure
import javax.inject.Inject

class MapViewModel @Inject constructor(app: Application, val useCase: MapUseCase) :
    BaseViewModel<MapUseCase>(app, useCase) {
    val mLoading = MutableLiveData<Boolean>()
    val mMapData = MutableLiveData<ArrayList<CaseModule>>()
    fun getLocations()
    {
        mLoading.value = true
        useCase.getLocations { result -> result.either(::locationFailed, ::locationSuccess)}
    }
    private fun locationSuccess(location: ArrayList<CaseModule>) {
        mLoading.value = false
        mMapData.value = location
    }
    private fun locationFailed(fail: Failure) {
        mLoading.value = false
        toastMutable.value = fail
    }
    }//mapViewModel