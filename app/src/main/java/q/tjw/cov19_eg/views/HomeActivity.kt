package q.tjw.cov19_eg.views

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.yarolegovich.slidingrootnav.SlideGravity
import com.yarolegovich.slidingrootnav.SlidingRootNav
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder
import kotlinx.android.synthetic.main.activity_home.*
import q.tjw.cov19_eg.R

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ViewBinding
    private lateinit var slidingRootNav: SlidingRootNav


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        val view = binding.root
        setContentView(view)
        initialization(savedInstanceState)

    }

    private fun initialization(savedInstanceState: Bundle?) {

        if (supportActionBar != null) {
            val actionBar = supportActionBar
            actionBar!!.hide()
        }

        slidingRootNav = SlidingRootNavBuilder(this)
            .withMenuOpened(false)
            .withMenuLocked(true)
            .withDragDistance(200)
            .withSavedState(savedInstanceState)
            .withMenuLayout(R.layout.drawer_home)
            .withContentClickableWhenMenuOpened(false)
            .inject()

        slidingRootNav.layout.setGravity(SlideGravity.RIGHT)

        slidingRootNav.layout.setOnTouchListener(OnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                slidingRootNav.closeMenu(true)
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    window.statusBarColor = ContextCompat.getColor(
//                        this,
//                        R.color.wit
//                    )
//                }
                return@OnTouchListener true
            }
            false
        })

    }

    fun openDrawerLayout(view: View) {
        if (slidingRootNav.isMenuOpened) {
            slidingRootNav.closeMenu()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.statusBarColor = ContextCompat.getColor(this, R.color.wit)
            }
        } else {
            slidingRootNav.openMenu(true)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.statusBarColor = ContextCompat.getColor(this, R.color.dark)
            }
        }
    }

    fun checkVirus(view: View) {}

    private fun startFragment(
        fragment: Fragment,
        textView: TextView
    ) {
        slidingRootNav.closeMenu(true)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.wit)
        }
//        current.setTextColor(ContextCompat.getColor(this, R.color.wit))
//        current = textView
//        current.setTextColor(ContextCompat.getColor(this, R.color.light))
        main_fragment.visibility = View.GONE
        Handler().postDelayed({
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_fragment, fragment).commit()
            main_fragment.visibility = View.VISIBLE
        }, 400)
    }
}
