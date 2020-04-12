package q.tjw.cov19_eg.map.service


import javax.inject.Singleton
/*
@Singleton
class LocationJopWorker @Inject constructor(private val app: Application,
                                            private val locationRequest: LocationRequest

)
{
     private val uniqueWorkName = "Work<TJW>Manger"
    val enableLocation: MutableLiveData<Response<Boolean>> = MutableLiveData()
    val location: MutableLiveData<Response<List<Location>>> = MutableLiveData()
    fun locationSetup() {
        enableLocation.value = Response.loading()
        LocationServices.getSettingsClient(app)
            .checkLocationSettings(
                LocationSettingsRequest.Builder()
                    .addLocationRequest(locationRequest)
                    .setAlwaysShow(true)
                    .build())
            .addOnSuccessListener { enableLocation.value = Response.success(true) }
            .addOnFailureListener {
                debugPrint("Gps not enabled")
                enableLocation.value = Response.error(it)
            }
    }
    fun trackLocation() {
        val locationWorker = PeriodicWorkRequestBuilder<TrackLocationWorker>(15, TimeUnit.MINUTES).addTag(uniqueWorkName).build()
        WorkManager.getInstance().enqueueUniquePeriodicWork(uniqueWorkName, ExistingPeriodicWorkPolicy.KEEP, locationWorker)
    }

    fun stopTrackLocation() {
        WorkManager.getInstance().cancelAllWorkByTag(uniqueWorkName)
    }

    fun getSavedLocation() {
        repository.location.getSavedLocation()
            .fromWorkerToMain(scheduler)
            .subscribeBy(
                onNext = {
                    location.value = Response.success(it)
                },
                onError = {
                  //  Timber.e(it, "Error in getting saved locations")
                    location.value = Response.error(it)
                }
            )
            .addTo(getCompositeDisposable())
    }
}

  class DailyWorker(ctx: Context, params: WorkerParameters) : CoroutineWorker(ctx, params) {
     @Inject
     lateinit var locationManger: LocationManger
      init {CO19Application.appComponent.inject(this)}
      override suspend fun doWork(): Result
      {
          locationManger.getLocation{
          }
          return Result.success()
      }
  }

data class Response<out T>(
    val status: Status,
    val data: T? = null,
    val error: Throwable? = null
) {
    enum class Status { LOADING, SUCCESS, ERROR
    }

    companion object {
        fun <T> loading(): Response<T> = Response(Status.LOADING, null, null)
        fun <T> success(data: T? = null): Response<T> = Response(Status.SUCCESS, data, null)
        fun <T> error(error: Throwable): Response<T> = Response(Status.ERROR, null, error)
    }
}

 */