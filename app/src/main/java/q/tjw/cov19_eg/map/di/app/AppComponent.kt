package q.tjw.cov19_eg.map.di.app

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import q.tjw.cov19_eg.SplashActivity
import q.tjw.cov19_eg.map.di.features.MainFeature
import q.tjw.cov19_eg.map.ui.MainMapActivity
import q.tjw.cov19_eg.map.ui.map_activity.MapFragment
import q.tjw.cov19_eg.map.ui.map_reportcase.AddCaseViewModel
import q.tjw.cov19_eg.map.ui.map_reportcase.FragmentAddCase
import q.tjw.cov19_eg.map.ui.profile.ProfileFragment
import q.tjw.cov19_eg.map.ui.status.FragmentWorldStatus
import q.tjw.cov19_eg.views.CheckActivity
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class, MainFeature::class,
        NetModule::class]
)
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun appModule(module: AppModule): Builder
        fun build(): AppComponent
        /**modules to be provided**/
    }
    fun inject(app: CO19Application)
    fun inject(fragmentAddCase: FragmentAddCase)
    fun inject(mapFragment: MapFragment)
    fun inject(addCaseViewModel: AddCaseViewModel)
    fun inject(fragmentWorldStatus: FragmentWorldStatus)
    fun inject(profileFragment: ProfileFragment)
    fun inject(checkActivity: CheckActivity)
    fun inject(splashActivity: SplashActivity)
  // fun inject(dailyWorker: DailyWorker)
}