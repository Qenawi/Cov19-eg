package q.tjw.cov19_eg.map.ui.map_reportcase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import q.tjw.cov19_eg.map.core.base.BaseUseCase
import q.tjw.cov19_eg.map.core.data.CaseModule
import q.tjw.cov19_eg.map.core.exception.Either
import q.tjw.cov19_eg.map.core.exception.Failure
import q.tjw.cov19_eg.map.core.extentions.InputType
import q.tjw.cov19_eg.map.core.extentions.ViewValidation
import q.tjw.cov19_eg.map.core.extentions.validateInput
import javax.inject.Inject

class AddCaseUseCase @Inject constructor(
    private val repo: AddCaseRepo,
    private val scope: CoroutineScope,
    private val dispatcher: CoroutineDispatcher
) :
    BaseUseCase<Any, AddCaseUseCase.Params>(scope, dispatcher) {
    fun reportCase(params: Params, call: (Either<Failure, Boolean>) -> Unit = {}) {
        repo.reportCase(params, call)
    }

    @ViewValidation
    fun validate(caseAddress: String?, caseName: String?):Boolean
    {
        return  caseAddress.validateInput(InputType.Age)&& caseName.validateInput(InputType.Name)
    }
    data class Params(val case: CaseModule)
}
