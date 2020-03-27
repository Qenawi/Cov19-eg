package q.tjw.cov19_eg.map.core.base

import kotlinx.coroutines.*
import javax.inject.Named
//todo  ReFactor Class To  use Remote and Local repo
abstract class BaseUseCase<out Type, in Params>
    (
    private val scope: CoroutineScope,
    private val dispatcher: CoroutineDispatcher
) where Type : Any
{

    fun onCleared()
    {
        if (scope.isActive) {
            scope.cancel()
        }
    }
    class None
}

