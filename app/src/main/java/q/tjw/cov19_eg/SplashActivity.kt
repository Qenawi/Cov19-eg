package q.tjw.cov19_eg
import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import pub.devrel.easypermissions.EasyPermissions
import q.tjw.cov19_eg.databinding.ActivitySplashBinding
import q.tjw.cov19_eg.map.core.exception.Failure
import q.tjw.cov19_eg.map.core.extentions.*
import q.tjw.cov19_eg.map.di.app.CO19Application
import q.tjw.cov19_eg.map.ui.MainMapActivity
import javax.inject.Inject


class SplashActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {
    companion object{
     const val USERS_COLLECTION="users"
    }
    private val loading = MutableLiveData<Boolean>().apply { this.value = false }
    private val requestCode = 1
    @Inject
    lateinit var  fireStore:FirebaseFirestore
    @SuppressLint("InlinedApi")
    private val permissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CO19Application.appComponent.inject(this)
        val bind =
            DataBindingUtil.setContentView<ActivitySplashBinding>(this, R.layout.activity_splash)
        bind.progress = loading
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        requestPermission()
    }

    private fun requestPermission() {
        if (EasyPermissions.hasPermissions(this, *permissions)) {
            checkIfRegistered()
        } else EasyPermissions.requestPermissions(
            this,
            getString(R.string.location_rationale),
            requestCode,
            *permissions
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
    private fun checkIfRegistered() {
        loading.value=true
        fireStore.collection(USERS_COLLECTION).document(getDeviceUniqueFootPrint()).get().addOnSuccessListener {snapShot->
            getPrefs()?.setIsLogin(snapShot.exists())
            continuaFlow()
        }
            .addOnFailureListener {e->e.printStackTrace()
            showToast(e.localizedMessage)
            }
    }
    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        checkIfRegistered()
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        showToast(Failure.SecurityError.f_causeSt)
        delay250 { finish() }
    }
    private fun continuaFlow()
    {
        mLaunchActivity<MainMapActivity>(contex = this)
        finish()
    }
}
