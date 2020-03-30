package q.tjw.cov19_eg.map.ui.map_activity

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import q.tjw.cov19_eg.map.core.data.CaseModule
import q.tjw.cov19_eg.map.core.data.dummyCaseModule
import q.tjw.cov19_eg.map.core.data.mToCaseModule
import q.tjw.cov19_eg.map.core.exception.Either
import q.tjw.cov19_eg.map.core.exception.Failure
import q.tjw.cov19_eg.map.core.exception.setArgs
import q.tjw.cov19_eg.map.core.extentions.mMapToJsonElement
import q.tjw.cov19_eg.map.core.extentions.mMapToObject
import q.tjw.cov19_eg.map.di.features.appFeatures.NetworkHandler
import javax.inject.Inject

interface MapRepo {
    companion object {
        private const val CaseUrl = "caseReports"
    }

    fun getLocations(call: (Either<Failure, ArrayList<CaseModule>>) -> Unit = {})
    fun updateCurrentUserState(call: (Either<Failure, Boolean>) -> Unit = {})
    class NetWork @Inject constructor(
       private val fStore: FirebaseFirestore,
       private val network: NetworkHandler
    ) : MapRepo {
        override fun getLocations(call: (Either<Failure, ArrayList<CaseModule>>) -> Unit) {
            when (network.isConnected) {
                null, false -> call(Either.Left(Failure.NetworkConnection))
                true -> fStore.collection(CaseUrl).get()
                    .addOnSuccessListener { result ->
                             val res = ArrayList<CaseModule>()
                             for (doc in result) res.add(doc.data.mToCaseModule())
                        call(Either.Right(res))
                    }.addOnFailureListener {
                        printError(it)
                        call(Either.Left(Failure.ServerError))
                    }
            }
        }

        override fun updateCurrentUserState(call: (Either<Failure, Boolean>) -> Unit) {}
    }
}



fun printError(t: Throwable?) {
    Log.v("CO19Error", "${t?.message}")
}
fun <T:Any?> debugPrint(toPrint:T)=
Log.v("Co19Debug","$toPrint")