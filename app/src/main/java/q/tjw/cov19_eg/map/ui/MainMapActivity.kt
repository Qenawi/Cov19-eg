package q.tjw.cov19_eg.map.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import kotlinx.android.synthetic.main.bottom_bar.*
import q.tjw.cov19_eg.R
import q.tjw.cov19_eg.databinding.ActivityMapBinding
import q.tjw.cov19_eg.map.core.base.BaseActivity
import q.tjw.cov19_eg.map.core.base.BaseFragment
import q.tjw.cov19_eg.map.core.extentions.*
import q.tjw.cov19_eg.map.ui.map_activity.MapFragment
import q.tjw.cov19_eg.map.ui.map_reportcase.FragmentAddCase
import q.tjw.cov19_eg.map.ui.profile.ProfileFragment
import q.tjw.cov19_eg.map.ui.status.FragmentWorldStatus
import q.tjw.cov19_eg.views.CheckActivity
import q.tjw.cov19_eg.views.RegisterActivity

class MainMapActivity : BaseActivity<ActivityMapBinding>() {
    private val homeFragment = MapFragment.newInstance()
    private val profileFragment = ProfileFragment.newInstance()
    private val statusFragment = FragmentWorldStatus.newInstance()
    val currentFrag =
        MutableLiveData<Navigation>().apply { this.value = Navigation.HomeMap }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addFragment(fragment())
        binding.lBottomBar.navigator = currentFrag
        binding.appToolBar.callback = this.callBack
        binding.appToolBar.title = this.toolbarTitle
        navigation.setOnNavigationItemSelectedListener { b ->
            val fragment: BaseFragment? = when (b.itemId.idToNavigation()) {
                currentFrag.value -> null
                Navigation.HomeMap -> homeFragment
                Navigation.Status -> statusFragment
                Navigation.Profile -> profileFragment
            }
            delay250 { fragment?.let { f -> mAddFragment(false, this@MainMapActivity) { f } } }
            return@setOnNavigationItemSelectedListener true
        }

    }

    override fun layoutId() = R.layout.activity_map
    override fun fragment(): BaseFragment = MapFragment.newInstance()
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == MapFragment.ActivityResult && resultCode == Activity.RESULT_OK) {
            when (RegisterActivity.open_next) {

                q.tjw.cov19_eg.map.core.data.Navigation.AddCase -> mAddFragment(activity = this){FragmentAddCase.newInstance()}
                q.tjw.cov19_eg.map.core.data.Navigation.CheckActivity -> mLaunchActivity<CheckActivity>(contex = this)
                else -> TODO()
            }
        }
    }

}