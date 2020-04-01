package q.tjw.cov19_eg.map.ui.map_reportcase

import com.google.firebase.firestore.FirebaseFirestore
import q.tjw.cov19_eg.map.core.exception.Either
import q.tjw.cov19_eg.map.core.exception.Failure
import q.tjw.cov19_eg.map.di.features.NetworkHandler
import q.tjw.cov19_eg.map.ui.map_activity.printError
import javax.inject.Inject

interface AddCaseRepo {
    companion object {private const val CaseUrl = "caseReports"}
    fun reportCase(params: AddCaseUseCase.Params, call: (Either<Failure, Boolean>) -> Unit = {})
    class NetWork @Inject constructor(
        private val fStore: FirebaseFirestore,
        private val network: NetworkHandler
    ) : AddCaseRepo {
        override fun reportCase(
            params: AddCaseUseCase.Params,
            call: (Either<Failure, Boolean>) -> Unit
        ) {
            when (network.isConnected) {
                null, false -> call(Either.Left(Failure.NetworkConnection))
                true -> fStore.collection(CaseUrl).add(params.case).addOnSuccessListener { result ->
                    val debug_result = result
                    call(Either.Right(true))
                }
                    .addOnFailureListener {
                        printError(it)
                        call(Either.Left(Failure.ServerError))
                    }
            }
        }//report case
    }// network ->r epo
}