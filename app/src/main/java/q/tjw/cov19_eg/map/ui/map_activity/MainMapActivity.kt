package q.tjw.cov19_eg.map.ui.map_activity

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import q.tjw.cov19_eg.R
import q.tjw.cov19_eg.databinding.MapActivityBinding
import q.tjw.cov19_eg.map.core.extentions.observe
import q.tjw.cov19_eg.map.core.extentions.setUpMap
import q.tjw.cov19_eg.map.core.extentions.viewModel
import q.tjw.cov19_eg.map.di.app.CO19Application
import javax.inject.Inject

class MainMapActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var bind: MapActivityBinding
    private lateinit var map: GoogleMap
    private lateinit var viewModel: MapViewModel
    @Inject
    lateinit var factory: ViewModelProvider.Factory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CO19Application.appComponent.inject(this)
        bind = DataBindingUtil.setContentView<MapActivityBinding>(this, R.layout.map_activity)
        bind.lifecycleOwner = this
        val mapFragment = supportFragmentManager.findFragmentById(R.id.v_map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        viewModel = viewModel(factory) {
            bind.viewModel = this
            observe(mMapData) { covCases ->
                covCases?.let { case ->
                   debugPrint(case)
                } }
        }
        viewModel.getLocations()
    }
    override fun onMapReady(p0: GoogleMap?) {
        p0?.let {
           setUpMap(p0)
            map = p0
        }
    }
}