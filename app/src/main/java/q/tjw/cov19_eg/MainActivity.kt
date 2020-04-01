package q.tjw.cov19_eg
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import q.tjw.cov19_eg.map.ui.MainMapActivity
class MainActivity : AppCompatActivity()
{override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        startActivity(Intent(this, MainMapActivity::class.java))
    } }
