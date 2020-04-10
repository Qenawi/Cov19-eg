package q.tjw.cov19_eg.views

import android.app.Dialog
import android.app.ProgressDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Location
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import q.tjw.cov19_eg.R
import q.tjw.cov19_eg.databinding.ActivityCheckBinding
import q.tjw.cov19_eg.map.core.data.CaseModule
import q.tjw.cov19_eg.map.core.data.emptyI
import q.tjw.cov19_eg.map.core.data.emptyS
import q.tjw.cov19_eg.map.core.extentions.getDeviceUniqueFootPrint
import q.tjw.cov19_eg.map.core.extentions.mVisible
import q.tjw.cov19_eg.map.core.extentions.observe
import q.tjw.cov19_eg.map.di.app.CO19Application
import q.tjw.cov19_eg.map.di.features.LocationManger
import java.util.*
import javax.inject.Inject

class CheckActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheckBinding
    private val check = listOf(0, 2, 2, 1, 0, 1, 4, 3, 2, 5)
    private var cont: Int = 0
    private var result: String? = null
    private var travelOnly: Int = 0
    private var percentage: Int = 0
    private val toolbarTitle = MutableLiveData<String>()
    private val callBack = MutableLiveData<Boolean>()
    private var dialog: ProgressDialog? = null
    @Inject
    lateinit var db: FirebaseFirestore
    @Inject
    lateinit var locationManger: LocationManger
    private var location: com.google.android.gms.maps.model.LatLng? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CO19Application.appComponent.inject(this)
        binding = ActivityCheckBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.lToolBar.title =
            toolbarTitle.apply { this.value = resources.getString(R.string.check) }
        binding.lToolBar.callback = callBack
        initialization()
    }

    private fun initialization() {
        FirebaseApp.initializeApp(this)
        dialog = ProgressDialog(this)
        dialog?.setMessage(resources?.getString(R.string.please_wait))
        observe(callBack) { a -> if (a == true) onBackPressed() }
        locationManger.getLocation { a ->
            a.either({}, ::handleLocation)
        }
    }

    private fun handleLocation(latLng: Location) {
        location = LatLng(latLng.latitude, latLng.longitude)
    }

    private fun addUserState() {
        val caseModule = CaseModule(
            emptyS, emptyS,
            Calendar.getInstance().timeInMillis.toString(),
            false,
            location?.latitude.toString(), location?.longitude.toString(),
            getDeviceUniqueFootPrint(), emptyI, emptyS, percentage
        )
        db.collection("caseReports").document(getDeviceUniqueFootPrint()).set(caseModule)
            .addOnSuccessListener {
                showResult(result ?: emptyS)
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, R.string.register_failed, Toast.LENGTH_SHORT).show()
            }
    }

    fun check(view: View) {
        cont = 0
        travelOnly = 0
        if (binding.checkbox1.isChecked) {
            cont += check[1]
            travelOnly = 0
        }
        if (binding.checkbox2.isChecked) {
            cont += check[2]
            travelOnly = 0
        }
        if (binding.checkbox3.isChecked) {
            cont += check[3]
            travelOnly = 0
        }
        if (binding.checkbox4.isChecked) {
            cont += check[4]
            travelOnly = 0
        }
        if (binding.checkbox5.isChecked) {
            cont += check[5]
            travelOnly = 0
        }
        if (binding.checkbox6.isChecked) {
            cont += check[6]
            travelOnly = 0
        }
        if (binding.checkbox7.isChecked) {
            cont += check[7]
            travelOnly = 0
        }
        if (binding.checkbox8.isChecked) {
            if (cont == 0 || travelOnly == 1) {
                travelOnly = 1
            } else {
                cont += check[8]
            }
        }
        if (binding.checkbox9.isChecked) {
            if (cont == 0 || travelOnly == 1) {
                travelOnly = 1
            } else {
                cont += check[9]
            }
        }

        percentage = ((cont / 20.0) * 100).toInt()
        when {
            cont == 0 -> {
                sendResult(resources.getString(R.string.you_are_ok))
            }
            cont <= 4 -> {
                sendResult(resources.getString(R.string.self_isolation))
            }
            cont == 5 -> {
                sendResult(resources.getString(R.string.call_doctor))
            }
            else -> {
                sendResult(resources.getString(R.string.call_hospital))
            }
        }
    }

    private fun showResult(result: String) {
        val dialog = Dialog(this)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.custom_dialog)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        // set the custom dialog components - text, image and button
        val ok: Button = dialog.findViewById(R.id.ok)
        val title: carbon.widget.TextView = dialog.findViewById(R.id.title)
        val percentageView: carbon.widget.TextView = dialog.findViewById(R.id.percentage)
        title.text = result
        percentageView.text = if (travelOnly == 1 || cont == 0) {
            ""
        } else {
            resources.getString(
                R.string.infection_percentage,
                "%$percentage"
            )
        }
        dialog.show()
        ok.setOnClickListener {
            dialog.dismiss()
            finish()
        }
    }

    private fun sendResult(result: String) {
        this.result = result
        addUserState()
    }

    fun checkLayout(view: View) {
        when (view.id) {
            R.id.check1_layout -> {
                binding.checkbox1.isChecked = !binding.checkbox1.isChecked
            }
            R.id.check2_layout -> {
                binding.checkbox2.isChecked = !binding.checkbox2.isChecked
            }
            R.id.check3_layout -> {
                binding.checkbox3.isChecked = !binding.checkbox3.isChecked
            }
            R.id.check4_layout -> {
                binding.checkbox4.isChecked = !binding.checkbox4.isChecked
            }
            R.id.check5_layout -> {
                binding.checkbox5.isChecked = !binding.checkbox5.isChecked
            }
            R.id.check6_layout -> {
                binding.checkbox6.isChecked = !binding.checkbox6.isChecked
            }
            R.id.check7_layout -> {
                binding.checkbox7.isChecked = !binding.checkbox7.isChecked
            }
            R.id.check8_layout -> {
                binding.checkbox8.isChecked = !binding.checkbox8.isChecked
            }
            R.id.check9_layout -> {
                binding.checkbox9.isChecked = !binding.checkbox9.isChecked
            }
        }
    }


}
