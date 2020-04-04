package q.tjw.cov19_eg

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import q.tjw.cov19_eg.map.ui.MainMapActivity

class SplashActivity : AppCompatActivity() {

    val PERMISSION_ALL = 1
    private val permissions = arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)

    //    startActivity(Intent(this,MainMapActivity::class.java))

        permissions()
    }

    private fun permissions() {

        if (!hasPermissions()) {
            ActivityCompat.requestPermissions(this, permissions, PERMISSION_ALL)
        } else {
            sleep()
        }
    }

    private fun hasPermissions(): Boolean {

            for (permission in permissions) {
                if (ActivityCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                    return false
                }

        }
        return true
    }

    private fun sleep() {
        val thread: Thread = object : Thread() {
            override fun run() {
                try {
                    sleep(1500)
                    intent = Intent(this@SplashActivity , MainMapActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                    finish()
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }
        thread.start()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            1 -> if (grantResults.isNotEmpty()
                && grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                sleep()
            } else {
                sleep()
            }
        }
    }
}
