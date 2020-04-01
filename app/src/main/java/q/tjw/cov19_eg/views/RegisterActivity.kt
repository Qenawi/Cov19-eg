package q.tjw.cov19_eg.views

import android.app.Activity
import android.app.ProgressDialog
import android.os.Bundle
import android.provider.Settings.Secure
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import q.tjw.cov19_eg.R
import q.tjw.cov19_eg.databinding.ActivityRegisterBinding
import q.tjw.cov19_eg.model.User
import q.tjw.cov19_eg.utilities.Validation


class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private var provinceAdapter: ArrayAdapter<String>? = null
    private var genderAdapter: ArrayAdapter<String>? = null
    private lateinit var user: User
    private var dialog: ProgressDialog? = null
    private val province = listOf("المحافظة", "القاهرة", "الجيزة", "القليوبية", "الإسكندرية", "البحيرة", "مطروح", "الدقهلية", "كفر الشيخ", "الغربية",
        "المنوفية", "دمياط", "بورسعيد", "الإسماعيلية", "السويس", "الشرقية", "شمال سيناء", "جنوب سيناء", "بني سويف", "المنيا", "الفيوم", "أسيوط",
        "الوادي الجديد", "سوهاج", "قنا", "الأقصر", "أسوان", "البحر الأحمر")

    private val gender = listOf("النوع", "ذكر", "أنثى")


    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initialization()
    }

    private fun initialization() {
        FirebaseApp.initializeApp(this)
        dialog = ProgressDialog(this)
        dialog?.setMessage(resources?.getString(R.string.please_wait))
        db = FirebaseFirestore.getInstance()
        provinceAdapter = ArrayAdapter<String>(this,
            R.layout.spinner_selected_item,
            R.id.item, province)
        genderAdapter = ArrayAdapter<String>(this,
            R.layout.spinner_selected_item,
            R.id.item, gender)
        binding.provinceSpinner.adapter = provinceAdapter
        binding.genderSpinner.adapter = genderAdapter

    }

    fun register(view: View) {
        Log.v("a7a", getDeviceUniqueID())
        var valid: String = Validation.registerValidation(
            binding.phone.text.toString(),
            binding.name.text.toString(),
            binding.provinceSpinner.selectedItemPosition,
            binding.age.text.toString(),
            binding.genderSpinner.selectedItemPosition
            )
        if (valid == "valid") {
            dialog?.show()
            user = User(binding.name.text.toString(),
                binding.phone.text.toString(), province[binding.provinceSpinner.selectedItemPosition],
                binding.age.text.toString(), gender[binding.genderSpinner.selectedItemPosition])

            db.collection("users").document(getDeviceUniqueID()).set(user)
                .addOnSuccessListener {
                    Toast.makeText(this, R.string.register_success, Toast.LENGTH_SHORT).show()
                    dialog?.dismiss()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, R.string.register_failed, Toast.LENGTH_SHORT).show()
                    Log.d("a7a", e.toString())
                    dialog?.dismiss()
                }
        }
        else{
            Toast.makeText(this, valid, Toast.LENGTH_SHORT).show()
        }
    }

    private fun getDeviceUniqueID(): String {
        return Secure.getString(contentResolver,
            Secure.ANDROID_ID
        )
    }

}
