package q.tjw.cov19_eg.map.ui.map_activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import kotlinx.android.synthetic.main.map_activity.*
import q.tjw.cov19_eg.R
import q.tjw.cov19_eg.databinding.MapActivityBinding
import q.tjw.cov19_eg.map.core.base.BaseActivity
import q.tjw.cov19_eg.map.core.base.BaseFragment
import q.tjw.cov19_eg.map.core.extentions.*
import q.tjw.cov19_eg.map.di.app.CO19Application
import q.tjw.cov19_eg.map.ui.MainMapActivity
import q.tjw.cov19_eg.map.ui.map_reportcase.ActivityAddCase
import q.tjw.cov19_eg.utilities.SharedPreference
import q.tjw.cov19_eg.views.CheckActivity
import q.tjw.cov19_eg.views.RegisterActivity
import javax.inject.Inject

class MapFragment : BaseFragment(), OnMapReadyCallback {
    companion object { fun newInstance() = MapFragment() }
    private lateinit var intent: Intent
    private lateinit var map: GoogleMap
    private lateinit var viewModel: MapViewModel
    @Inject
    lateinit var factory: ViewModelProvider.Factory
    override fun layoutId() = R.layout.map_activity
    override fun view_life_cycle_owner() = viewLifecycleOwner
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CO19Application.appComponent.inject(this)
        viewModel = viewModel(factory) {
            observe(mMapData) { covCases ->
                covCases?.let { case ->
                    map.mMapAddMarkers(case)
                }
            }
        }

    }
    override fun onMapReady(p0: GoogleMap?) {
        p0?.let {
            activity?.setUpMap(p0)
            map = p0
            viewModel.getLocations()
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        (binding as MapActivityBinding).viewModel = viewModel
        val mapFragment = childFragmentManager.findFragmentById(R.id.v_map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        l_addCase.setOnClickListener {
            delay250 {a->
                val sharedPreference = SharedPreference(CO19Application.context!!)
                if(sharedPreference.getIsLogin()==true)
                addCase()
                else
                {
                    intent = Intent(activity, RegisterActivity::class.java)
                    startActivity(intent)
                }
            }
        }
        l_updateProfile.setOnClickListener{
            val sharedPreference = SharedPreference(CO19Application.context!!)
            if (sharedPreference.getIsLogin()!!) {
                startActivity(Intent(activity, CheckActivity::class.java))
            }
            else{
                intent = Intent(activity, RegisterActivity::class.java)
                startActivity(intent)

            }
        }
    }
    private fun addCase() = mLaunchActivity<ActivityAddCase>(contex = context)
    override fun onResume() {
        super.onResume()
        (activity as MainMapActivity?)?.currentFrag?.postValue(Navigation.HomeMap)
        (activity as BaseActivity<*>).toolbarTitle.postValue(getString(R.string.l_worldMap))
    }
}


