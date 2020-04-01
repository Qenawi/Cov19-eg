package q.tjw.cov19_eg.map.ui

import android.os.Bundle
import q.tjw.cov19_eg.R
import q.tjw.cov19_eg.databinding.ActivityMapBinding
import q.tjw.cov19_eg.map.core.base.BaseActivity
import q.tjw.cov19_eg.map.core.base.BaseFragment
import q.tjw.cov19_eg.map.ui.map_activity.MapFragment

class MainMapActivity : BaseActivity<ActivityMapBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addFragment(fragment())
    }
    override fun layoutId()=R.layout.activity_map
    override fun fragment():BaseFragment=
        MapFragment.newInstance()

}