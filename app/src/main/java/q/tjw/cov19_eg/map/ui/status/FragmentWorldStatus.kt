package q.tjw.cov19_eg.map.ui.status

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
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
import q.tjw.cov19_eg.utilities.AppUtils
import javax.inject.Inject

class FragmentWorldStatus : BaseFragment() {
    private val egyptState = MutableLiveData<WorldLifeStateModule>()
    private val worldState = MutableLiveData<WorldLifeStateModule>()
    @Inject
    lateinit var fireStore: FirebaseFirestore

    @Inject
    lateinit var connectionApi: ConnectionApi
    private lateinit var casesAdapter: CountryCasesAdapter
    private lateinit var layoutManager: LinearLayoutManager

    var worldStatusVM: WorldStatusVM? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CO19Application.appComponent.inject(this)
    }

    private fun initialization() {
        worldStatusVM = activity?.let { ViewModelProviders.of(it).get(WorldStatusVM::class.java) }
        worldStatusVM!!.init(connectionApi)
        layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        (binding as MapWorldStatusBinding).countriesRecycler.layoutManager = layoutManager

    }

    private fun getCases() {
        if (AppUtils.isConnected(requireActivity())) {
            worldStatusVM?.getCases()
            worldStatusVM?.getCasesResponse()?.observe(viewLifecycleOwner,
                Observer<ArrayList<CasesResponse>> { countryCases: ArrayList<CasesResponse>? ->

                    if (countryCases != null) {
                        var egyptIndex: Int = countryCases!!.indexOfFirst { it.country == "Egypt" }
                        var egypt: CasesResponse? = countryCases!!.find { it.country == "Egypt" }
                        countryCases.drop(egyptIndex)
                        countryCases.add(0, egypt!!)
                        showCases(countryCases)
                        listeners()

                    }
                    else{
                        Toast.makeText(activity, "حدث خطأ ما", Toast.LENGTH_SHORT).show()

                    }
                })
        }
        else{
            Toast.makeText(activity, "تاكد من الاتصال بالانترنت", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showCases(countryCases: List<CasesResponse>?) {
        casesAdapter = CountryCasesAdapter(countryCases!!)
        (binding as MapWorldStatusBinding).countriesRecycler.adapter = casesAdapter
        (binding as MapWorldStatusBinding).countriesRecycler.visibility = View.VISIBLE
        (binding as MapWorldStatusBinding).progressBar.visibility = View.GONE

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        fetchState()
//        (binding as MapWorldStatusBinding).include.egyptModule = egyptState
//        (binding as MapWorldStatusBinding).include.worldModule = worldState

        initialization()
        getCases()
        return binding.root
    }

    private fun listeners() {
        (binding as MapWorldStatusBinding).search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (casesAdapter != null) {
                    casesAdapter.filter.filter(s)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })

        (binding as MapWorldStatusBinding).search.setOnEditorActionListener { _, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    activity?.let { AppUtils.hideKeyboard(it) }
                    true
                }
                else -> false
            }
        }

        (binding as MapWorldStatusBinding).countriesRecycler.setOnTouchListener(OnTouchListener { _, _ ->
            activity?.let { AppUtils.hideKeyboard(it) }
            false
        })

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