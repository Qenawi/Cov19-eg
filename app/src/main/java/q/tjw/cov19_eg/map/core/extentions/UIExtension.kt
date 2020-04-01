package q.tjw.cov19_eg.map.core.extentions

import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.View
import androidx.databinding.BindingAdapter
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.chip.Chip
import com.google.type.LatLng
import kotlinx.coroutines.*
import q.tjw.cov19_eg.R
import q.tjw.cov19_eg.map.core.extentions.InputType.*


fun Activity.cHideSoftKeyboard() {
    val focusedView = currentFocus
    focusedView?.let { view ->
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

@BindingAdapter("loadImageFromUrl", "load_as_cover", requireAll = false)
fun cLoadImageFromUrl(view: ImageView, url: String?, cover: Boolean? = false) {

    val cache = true
    fun Boolean?.getDiskStrategy() = this?.run {
        if (this) DiskCacheStrategy.ALL
        else DiskCacheStrategy.NONE
    } ?: DiskCacheStrategy.NONE

    val requestOptions = RequestOptions().apply {
        if (cover == true) {
            //placeholder(R.color.carbon_white)
            error(R.color.colorAccent).placeholder(R.color.colorAccent)
        } else {
            //  this.placeholder(R.drawable.user)
            error(R.color.colorPrimary).placeholder(R.color.colorPrimary)
        }
    }
    Glide.with(view.context).applyDefaultRequestOptions(requestOptions).load("$url")
        .diskCacheStrategy(cache.getDiskStrategy())
        .into(view)
}

fun View.cVisible(boolean: Boolean?) {
    boolean?.let {
        if (boolean) this.visibility = View.VISIBLE
        else this.visibility = View.INVISIBLE
    }
}

fun View.cEnable(boolean: Boolean?) =
    when (boolean) {
        true -> {
            this.isEnabled = true
        }
        false -> {
            this.isEnabled = false
        }
        null -> {
        }
    }

fun EditText.cTextExtractor(): String {
    return if (this.text.isNullOrEmpty()) ""
    else this.text.toString()
}

fun Spinner.cPopulate(arrayListID: Int) {
    this.context?.let { ctx ->
        val mArray = ctx.resources.getStringArray(arrayListID)
        val adb = ArrayAdapter<String>(ctx, android.R.layout.simple_spinner_dropdown_item, mArray)
        this.adapter = adb
    }
}

@BindingAdapter("lock_view")
fun View.mLock(load: Boolean?) = load?.let { bool -> this.mEnable(!bool) }

fun View.mEnable(boolean: Boolean?) =
    when (boolean) {
        true -> {
            this.isEnabled = true
        }
        false -> {
            this.isEnabled = false
        }
        null -> {
        }
    }

fun View.mVisible(boolean: Boolean?) {
    boolean?.let {
        if (boolean) this.visibility = View.VISIBLE
        else this.visibility = View.INVISIBLE
    }
}

@BindingAdapter("progress_view")
fun View.mProgress(boolean: Boolean?) {
    mVisible(boolean)
}

@BindingAdapter("safe_text")
fun TextView.mText(any: Any?) = any?.let { sText -> text = sText.toString() }

@BindingAdapter(
    value = ["chip_date_activation",
        "chip_location_activation"],
    requireAll = false
)
fun Chip.mChipState(date: String?, latLng: com.google.android.gms.maps.model.LatLng?) {
    val activeColor = R.color.carbon_green_600
    val inActiveColor = R.color.carbon_black
    if (date != null || latLng != null) this.chipBackgroundColor =
        ColorStateList.valueOf(this.context.resources.getColor(activeColor))
    else ColorStateList.valueOf(this.context.resources.getColor(inActiveColor))
}

@Target(AnnotationTarget.FUNCTION)
@kotlin.annotation.Retention
annotation class Delay(val amount: Int)

@Target(AnnotationTarget.FUNCTION)
@kotlin.annotation.Retention
annotation class ViewValidation


fun <T:Any?>T?.validateInput(inputType: InputType):Boolean
=this?.let {input->
 return@let when(inputType){
    Name ->  (input as String).length>=10
    Address -> (input as String).length>=20
    Email -> false
    Password -> false
    LocationString -> (input as String).length>=10
    LocationLatLng -> true
     Age ->  (input as String).length>=2
 }
}?:false



enum class InputType {
    Age,
    Name,
    Address,
    Email,
    Password, LocationString,
    LocationLatLng
}
fun delay250(block: (Any) -> Unit = {}){
    GlobalScope.launch {
        delay(250)
        withContext(Dispatchers.Main)
        { block(Any()) }
    }
}