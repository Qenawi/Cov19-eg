package q.tjw.cov19_eg.map.ui.map_reportcase
import android.app.Application
import android.location.Location
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import q.tjw.cov19_eg.map.core.base.BaseViewModel
import q.tjw.cov19_eg.map.core.data.CaseModule
import q.tjw.cov19_eg.map.core.data.emptyB
import q.tjw.cov19_eg.map.core.data.emptyS
import q.tjw.cov19_eg.map.core.exception.Failure
import q.tjw.cov19_eg.map.core.extentions.ViewValidation
import q.tjw.cov19_eg.map.core.extentions.getDeviceUniqueFootPrint
import q.tjw.cov19_eg.map.di.app.CO19Application
import q.tjw.cov19_eg.map.di.features.LocationManger
import java.util.*
import javax.inject.Inject

class AddCaseViewModel @Inject constructor(
    app: Application,
    val useCase: AddCaseUseCase
) :
    BaseViewModel<AddCaseUseCase>(app, useCase) {
    @Inject
    lateinit var locationManger: LocationManger
    init {
        CO19Application.appComponent.inject(this)
        locationManger.getLocation {
            it.either(::locationFailed, ::locationSuccess)
        }
    }
    val mLoading = MutableLiveData<Boolean>().apply { this.value = false }
    val mCaseResult = MutableLiveData<Boolean>()
    val mCaseDate = MutableLiveData<String>().apply {
        this.postValue(Calendar.getInstance().timeInMillis.toString())
    }
    val mCaseLocation = MutableLiveData<LatLng>()
    val caseAddress = MutableLiveData<String>().apply { this.value = emptyS }
    val caseName = MutableLiveData<String>().apply { this.value = emptyS }
    val caseConfirmed = MutableLiveData<Boolean>().apply { this.value = emptyB }
    private fun addCase(data: CaseModule) {
        mLoading.value = true
        useCase.reportCase(AddCaseUseCase.Params(data)) { result ->
            result.either(::caseFailed, ::caseSuccess)
        }
    }
    private fun caseSuccess(boolean: Boolean) {
        mLoading.value = false
        mCaseResult.value = boolean
    }
    private fun caseFailed(fail: Failure) {
        mLoading.value = false
        toastMutable.value = fail
    }
    private fun locationFailed(fail: Failure) {
        toastMutable.value = fail
    }
    private fun locationSuccess(location: Location) =
        mCaseLocation.postValue(LatLng(location.latitude, location.longitude))
    @ViewValidation
    fun validate(view :View) {
        if (useCase.validate(caseAddress.value, caseName.value))
            addCase(
                CaseModule(
                     "Cairo",
                    caseName.value ?: emptyS, mCaseDate.value ?: emptyS,
                    caseConfirmed.value ?: emptyB, mCaseLocation.value?.latitude.toString(),
<<<<<<< HEAD
                    mCaseLocation.value?.longitude.toString(), "randomID",caseAddress.value?.toInt()?:22,"male", 50
=======
                    mCaseLocation.value?.longitude.toString(), getApplication<CO19Application>().getDeviceUniqueFootPrint(),caseAddress.value?.toInt()?:22,"male"
>>>>>>> MapCitySelectorFeature
                )
            )
        else toastMutable.postValue(Failure.ValidationError)

    }

    override fun onCleared() {
        super.onCleared()
        locationManger.clean()
    }
}