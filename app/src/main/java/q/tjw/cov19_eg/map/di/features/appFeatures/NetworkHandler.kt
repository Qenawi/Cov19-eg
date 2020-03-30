package q.tjw.cov19_eg.map.di.features.appFeatures
import android.content.Context
import q.tjw.cov19_eg.map.core.extentions.networkInfo
import javax.inject.Inject
import javax.inject.Singleton
@Singleton
class NetworkHandler @Inject constructor(private val context: Context)
{ val isConnected get() = context.networkInfo?.isConnected }