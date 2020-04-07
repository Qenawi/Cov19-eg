package q.tjw.cov19_eg.map.ui.map_reportcase

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import kotlinx.android.synthetic.main.bottom_bar.*
import q.tjw.cov19_eg.R
import q.tjw.cov19_eg.databinding.ActivityMapBinding
import q.tjw.cov19_eg.databinding.ActivityWithToolbarBinding
import q.tjw.cov19_eg.map.core.base.BaseActivity
import q.tjw.cov19_eg.map.core.base.BaseFragment
import q.tjw.cov19_eg.map.core.extentions.*
import q.tjw.cov19_eg.map.ui.map_activity.MapFragment
import q.tjw.cov19_eg.map.ui.profile.ProfileFragment
import q.tjw.cov19_eg.map.ui.status.FragmentWorldStatus

class ActivityAddCase : BaseActivity<ActivityWithToolbarBinding>() {

    val currentFrag =
        MutableLiveData<Navigation>().apply { this.value = Navigation.HomeMap }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addFragment(fragment())
        binding.appToolBar.callback=this.callBack
        binding.appToolBar.title=this.toolbarTitle
    }
    override fun layoutId() = R.layout.activity_with_toolbar
    override fun fragment(): BaseFragment = FragmentAddCase.newInstance()

}