package q.tjw.cov19_eg.map.di.features.appFeatures

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import q.tjw.cov19_eg.map.di.features.MainFeature
import q.tjw.cov19_eg.map.ui.map_activity.MapRepo
import q.tjw.cov19_eg.map.ui.map_activity.MapUseCase
import q.tjw.cov19_eg.map.ui.map_reportcase.AddCaseRepo
import q.tjw.cov19_eg.map.ui.map_reportcase.AddCaseUseCase
import javax.inject.Named
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Module
class AddCaseFeature {
    companion object{
        private  const val className="AddCaseFeature"
    }
    @Named(className + MainFeature.UseCaseScopeName)
    @Provides
    fun providesCoroutinesScope(): CoroutineScope = object : CoroutineScope
    {
        private val job = Job()
        override val coroutineContext: CoroutineContext
            get() = job + Dispatchers.IO
    }
    @Named(className + MainFeature.DispatcherUseCaseName)
    @Provides
    @Singleton
    fun provideCoroutinesMainDispatcher(): CoroutineDispatcher = Dispatchers.Main
    @Provides
    fun mapRepo(network: AddCaseRepo.NetWork): AddCaseRepo {return network}
    @Provides
    fun provideMapUseCase(repo:AddCaseRepo,
                          @Named(className + MainFeature.UseCaseScopeName)
                          scope: CoroutineScope,
                          @Named(className + MainFeature.DispatcherUseCaseName)
                          dispatcher: CoroutineDispatcher): AddCaseUseCase
    { return AddCaseUseCase(repo,scope,dispatcher) }

}