package q.tjw.cov19_eg.map.di.app
import android.app.Application
import com.google.firebase.FirebaseApp

class CO19Application : Application() {
    override fun onCreate() {
        super.onCreate()
        initDagger()
        initFireStore()
    }

    private fun initDagger() {
        appComponent =
            DaggerAppComponent.builder().application(this).appModule(AppModule(this)).build()
        appComponent.inject(this)

    }
    private fun initFireStore()
    { FirebaseApp.initializeApp(this) }
    companion object {
        lateinit var appComponent: AppComponent private set
    }
}