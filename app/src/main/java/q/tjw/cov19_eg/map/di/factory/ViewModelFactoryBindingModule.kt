package q.tjw.cov19_eg.map.di.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import q.tjw.cov19_eg.map.ui.map_activity.MapViewModel
import q.tjw.cov19_eg.map.ui.map_reportcase.AddCaseViewModel
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton
import kotlin.reflect.KClass

// Key
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@MapKey
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)

//Key
//Factory
@Suppress("UNCHECKED_CAST")
@Singleton
class ViewModelFactory
@Inject constructor(private val creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val creator =
            creators[modelClass] ?: creators.asIterable().firstOrNull { modelClass.isAssignableFrom(it.key) }?.value
            ?: throw IllegalArgumentException("unknown model class $modelClass")

        return try {
            creator.get() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}

//Factory
//Module
@Module
abstract class ViewModelFactoryBindingModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MapViewModel::class)
    abstract fun bindMapModel(vm: MapViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(AddCaseViewModel::class)
    abstract fun bindAddCaseViewModel(vm: AddCaseViewModel): ViewModel

    //Todo Add more ViewModels here
}