package q.tjw.cov19_eg.map.core.extentions
import android.content.Context
import android.widget.Toast
import q.tjw.cov19_eg.map.core.exception.Failure


var myToast: Toast? = null
fun Context.showToast(msg: String?) {
    msg?.let {
        myToast?.cancel()
        myToast = Toast.makeText(this, msg, Toast.LENGTH_SHORT)
        myToast?.show()
    }

}

fun Context.showToast(fail: Failure?) = fail?.let { showToast(it.f_causeSt) }