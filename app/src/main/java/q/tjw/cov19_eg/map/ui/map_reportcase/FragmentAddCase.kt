package q.tjw.cov19_eg.map.ui.map_reportcase
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import carbon.widget.DropDown
import kotlinx.android.synthetic.main.map_add_case.*
import q.tjw.cov19_eg.R
import q.tjw.cov19_eg.databinding.MapAddCaseBinding
import q.tjw.cov19_eg.map.core.base.BaseActivity
import q.tjw.cov19_eg.map.core.base.BaseFragment
import q.tjw.cov19_eg.map.core.extentions.observe
import q.tjw.cov19_eg.map.core.extentions.viewModel
import q.tjw.cov19_eg.map.di.app.CO19Application
import q.tjw.cov19_eg.views.RegisterActivity
import javax.inject.Inject
class FragmentAddCase : BaseFragment() {
    companion object {
        fun newInstance() = FragmentAddCase()
    }
    override fun layoutId() = R.layout.map_add_case
    override fun view_life_cycle_owner() = viewLifecycleOwner
    private lateinit var viewModel: AddCaseViewModel
    @Inject
    lateinit var factory: ViewModelProvider.Factory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CO19Application.appComponent.inject(this)
        viewModel = viewModel(factory) {
         observe(mCaseResult){result-> result?.let {
             activity?.finish()
         }
         }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (binding as MapAddCaseBinding).viewModel = viewModel
        (dropDown as DropDown<String>).setItems(RegisterActivity.province)
        (dropDown as DropDown<String>).setOnItemSelectedListener { item: String?, position: Int -> viewModel.selectedArea.postValue(item?:"") }
        l_male.setOnCheckedChangeListener { buttonView, isChecked -> l_female.setChecked(!isChecked) }
        l_female.setOnCheckedChangeListener { buttonView, isChecked ->l_male.setChecked(!isChecked)  }
        checkBox.setOnCheckedChangeListener { buttonView, isChecked -> viewModel.caseConfirmed.postValue(isChecked) }
    }

    override fun onResume() {
        super.onResume()
        (activity as BaseActivity<*>).toolbarTitle.postValue(getString(R.string.add_case))

    }
}