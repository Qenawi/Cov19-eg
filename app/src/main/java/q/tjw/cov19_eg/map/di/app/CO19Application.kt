package q.tjw.cov19_eg.map.di.app
import android.app.Application
import android.content.Context
import com.google.firebase.FirebaseApp
import q.tjw.cov19_eg.utilities.App

class CO19Application : Application() {
    override fun onCreate() {
        super.onCreate()
        initDagger()
        initFireStore()
        context = this;

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
        var context: Context? = null

    }
}