package q.tjw.cov19_eg.map.di.app

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import q.tjw.cov19_eg.map.di.features.MainFeature
import q.tjw.cov19_eg.map.ui.map_activity.MainMapActivity
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class, MainFeature::class]
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
    fun inject(app: MainMapActivity)
}