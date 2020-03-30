package q.tjw.cov19_eg.map.di.features

import dagger.Module
import q.tjw.cov19_eg.map.di.factory.ViewModelFactoryBindingModule
import q.tjw.cov19_eg.map.di.features.appFeatures.MapFeature

@Module(
    includes = [ViewModelFactoryBindingModule::class,
        MapFeature::class]
)
class MainFeature {
    companion object {
        const val UseCaseScopeName = "useCaseScope"
        const val DispatcherUseCaseName = "dispatcherName"
    }
}