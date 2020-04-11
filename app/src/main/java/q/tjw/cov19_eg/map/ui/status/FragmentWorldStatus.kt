package q.tjw.cov19_eg.map.ui.status

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.firestore.FirebaseFirestore
import q.tjw.cov19_eg.R
import q.tjw.cov19_eg.databinding.MapWorldStatusBinding
import q.tjw.cov19_eg.map.core.base.BaseActivity
import q.tjw.cov19_eg.map.core.base.BaseFragment
import q.tjw.cov19_eg.map.core.data.WorldLifeStateModule
import q.tjw.cov19_eg.map.core.data.mToWorldLifeStateModule
import q.tjw.cov19_eg.map.core.extentions.Navigation
import q.tjw.cov19_eg.map.data_layer.model.world_cases.CasesResponse
import q.tjw.cov19_eg.map.data_layer.reprository.ConnectionApi
import q.tjw.cov19_eg.map.di.app.CO19Application
import q.tjw.cov19_eg.map.ui.MainMapActivity
import javax.inject.Inject

class FragmentWorldStatus : BaseFragment() {
    private val egyptState = MutableLiveData<WorldLifeStateModule>()
    private val worldState = MutableLiveData<WorldLifeStateModule>()
    @Inject
    lateinit var fireStore: FirebaseFirestore

    @Inject
    lateinit var connectionApi: ConnectionApi

    var worldStatusVM: WorldStatusVM? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CO19Application.appComponent.inject(this)

        initialization()
    }

    private fun initialization() {
        worldStatusVM = activity?.let { ViewModelProviders.of(it).get(WorldStatusVM::class.java) }
        worldStatusVM!!.init(connectionApi)

        worldStatusVM?.getCases()
        worldStatusVM?.getCasesResponse()?.observe(this,
            Observer<CasesResponse> { casesResponse: CasesResponse? ->
                Log.v("a7a", casesResponse.toString())
            })

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        fetchState()
        (binding as MapWorldStatusBinding).include.egyptModule = egyptState
        (binding as MapWorldStatusBinding).include.worldModule = worldState

        return binding.root
    }

    companion object {
        fun newInstance() = FragmentWorldStatus()
    }

    override fun layoutId() = R.layout.map_world_status
    override fun view_life_cycle_owner() = viewLifecycleOwner
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        (activity as MainMapActivity?)?.currentFrag?.postValue(Navigation.Status)
        (activity as BaseActivity<*>).toolbarTitle.postValue(getString(R.string.l_worldStatus))
    }
    //todo 7amda Fix Get State

    private fun fetchState() {
        fireStore.collection("live-state").get().addOnSuccessListener { query ->
            for (doc in query) {
                if (doc.id == "eg-state") {
                    egyptState.postValue(doc.data.mToWorldLifeStateModule())
                } else {
                    worldState.postValue(doc.data.mToWorldLifeStateModule())
                }
            }
        }
    }
}