package q.tjw.cov19_eg.map.core.base

import android.content.Intent
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import q.tjw.cov19_eg.map.core.extentions.inTransaction
import android.view.View
import androidx.lifecycle.MutableLiveData
import q.tjw.cov19_eg.R
import q.tjw.cov19_eg.map.core.extentions.cHideSoftKeyboard
import q.tjw.cov19_eg.map.core.extentions.observe


abstract class BaseActivity<B : ViewDataBinding> : AppCompatActivity() {
    abstract fun layoutId(): Int
    lateinit var binding: B
    private val fragHolderId = R.id.l_fragment_holder
    val toolbarTitle = MutableLiveData<String>()
    val callBack = MutableLiveData<Boolean>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId())
        binding.lifecycleOwner = this
        binding.root.layoutDirection = View.LAYOUT_DIRECTION_LOCALE
        this.observe(callBack) { click -> click?.let { onNavigation() } }
        cHideSoftKeyboard()

    }

    fun addFragment(fragment: Fragment,addToStack: Boolean?=null) {
        if(addToStack==true)
        addFragmentHelperWithBackStack(fragment)
        else addFragmentHelper(fragment)
    }
    private fun addFragmentHelperWithBackStack(fragment: Fragment) = supportFragmentManager.inTransaction {
        replace(
            fragHolderId, fragment
        )
    }
    private fun addFragmentHelper(fragment: Fragment) = supportFragmentManager.inTransaction {
        replace(
            fragHolderId, fragment
        )

    }

    abstract fun fragment(): BaseFragment
    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            (supportFragmentManager.findFragmentById(fragHolderId) as BaseFragment).onBackPressed()
            super.onBackPressed()
            } else {
            finish()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        supportFragmentManager.fragments[supportFragmentManager.fragments.size - 1]?.onActivityResult(
            requestCode,
            resultCode,
            data
        )
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {

        if (keyCode == KeyEvent.KEYCODE_HOME) {
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
   private fun onNavigation() {
        onBackPressed()
    }


}
//enterprises/LC00s6lub4