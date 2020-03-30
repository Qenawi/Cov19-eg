package q.tjw.cov19_eg.map.ui.map_activity

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import q.tjw.cov19_eg.map.core.base.BaseUseCase
import q.tjw.cov19_eg.map.core.data.CaseModule
import q.tjw.cov19_eg.map.core.exception.Either
import q.tjw.cov19_eg.map.core.exception.Failure
import java.security.AccessControlContext
import javax.inject.Inject

class MapUseCase @Inject constructor(
    val repo: MapRepo,
    val scope: CoroutineScope,
    val dispatcher: CoroutineDispatcher
) : BaseUseCase<Any, MapUseCase.Params>(scope, dispatcher) {
    class Params()
    fun addCase(call: (Either<Failure, Boolean>) -> Unit = {}) { //todo
    }
    fun getLocations(call: (Either<Failure, ArrayList<CaseModule>>) -> Unit = {})
    {
        repo.getLocations(call)
    }
}