package q.tjw.cov19_eg.map.ui.map_activity
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
import q.tjw.cov19_eg.map.ui.map_reportcase.FragmentAddCase
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton
class MapFragment : BaseFragment(), OnMapReadyCallback {
    companion object {
        fun newInstance() = MapFragment()
    }
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
        viewModel.getLocations()
    }
    override fun onMapReady(p0: GoogleMap?) {
        p0?.let {
            activity?.setUpMap(p0)
            map = p0
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
            delay250 { a -> addCase() }
        }
    }

    private fun addCase() = mAddFragment(activity) { FragmentAddCase.newInstance() }
    override fun onResume() {
        super.onResume()
        (activity as BaseActivity<*>).toolbarTitle.postValue(getString(R.string.l_worldMap))
    }
}


