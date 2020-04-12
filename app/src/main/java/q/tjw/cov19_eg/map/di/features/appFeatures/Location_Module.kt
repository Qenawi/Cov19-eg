package q.tjw.cov19_eg.map.di.features.appFeatures
import android.app.Application
import android.content.Context
import android.location.Geocoder
import android.location.LocationManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import dagger.Module
import dagger.Provides
import java.util.*
import javax.inject.Singleton
@Module
class Location_Module {

    @Singleton
    @Provides
    fun locationManger(app: Application): LocationManager {
        return app.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }
    @Singleton
    @Provides
    fun fusedLocation(app: Application): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(app)

    }
    @Singleton
    @Provides
    fun decoderLocation(app: Application): Geocoder {
        return Geocoder(app, Locale.getDefault()) }


}