package q.tjw.cov19_eg.map.di.features.appFeatures

import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import q.tjw.cov19_eg.map.di.features.MainFeature.Companion.DispatcherUseCaseName
import q.tjw.cov19_eg.map.di.features.MainFeature.Companion.UseCaseScopeName
import q.tjw.cov19_eg.map.ui.map_activity.MapRepo
import q.tjw.cov19_eg.map.ui.map_activity.MapUseCase
import javax.inject.Named
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Module
class MapFeature {
    @Provides

    @Singleton
    fun fireStore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Named(UseCaseScopeName)
    @Provides
    fun providesCoroutinesScope(): CoroutineScope = object : CoroutineScope {
        private val job = Job()
        override val coroutineContext: CoroutineContext
            get() = job + Dispatchers.IO
    }

     @Named(DispatcherUseCaseName)
     @Provides
     @Singleton
     fun provideCoroutinesMainDispatcher(): CoroutineDispatcher = Dispatchers.Main
      @Provides
      fun mapRepo(network:MapRepo.NetWork):MapRepo{return network}

    @Provides
    fun provideMapUseCase(repo:MapRepo,
    @Named(UseCaseScopeName)
    scope: CoroutineScope,
    @Named(DispatcherUseCaseName)
    dispatcher: CoroutineDispatcher):MapUseCase
    { return MapUseCase(repo,scope,dispatcher) }
}