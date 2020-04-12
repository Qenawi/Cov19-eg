package q.tjw.cov19_eg.map.service



/*
@Singleton
class LocationRepository @Inject constructor(
    private val application: Application,
    private val database: Database
) {

    @SuppressLint("MissingPermission")
    fun getLocation() {
        /*
         * One time location request
         */
        if (application.isGPSEnabled() && application.checkLocationPermission()) {
            LocationServices.getFusedLocationProviderClient(application)
                ?.lastLocation
                ?.addOnSuccessListener { location: android.location.Location? ->
                    if (location != null)
                        saveLocation(Location(0, location.latitude, location.longitude, System.currentTimeMillis()))
                }
        }
    }

    private fun saveLocation(location: Location) = GlobalScope.launch { database.locationDao().insert(location) }

    fun getSavedLocation(): Flowable<List<Location>> = database.locationDao().selectAll()
}
 */