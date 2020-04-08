package q.tjw.cov19_eg.views

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.os.Bundle
import android.provider.Settings.Secure
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import q.tjw.cov19_eg.R
import q.tjw.cov19_eg.databinding.ActivityRegisterBinding
import q.tjw.cov19_eg.map.core.extentions.getDeviceUniqueFootPrint
import q.tjw.cov19_eg.map.core.extentions.mVisible
import q.tjw.cov19_eg.map.core.extentions.observe
import q.tjw.cov19_eg.model.User
import q.tjw.cov19_eg.utilities.SharedPreference
import q.tjw.cov19_eg.utilities.Validation


class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var sharedPreference: SharedPreference
    private var provinceAdapter: ArrayAdapter<String>? = null
    private var genderAdapter: ArrayAdapter<String>? = null
    private lateinit var user: User
    private var dialog: ProgressDialog? = null
    private val province = listOf("المحافظة", "القاهرة", "الجيزة", "القليوبية", "الإسكندرية", "البحيرة", "مطروح", "الدقهلية", "كفر الشيخ", "الغربية",
        "المنوفية", "دمياط", "بورسعيد", "الإسماعيلية", "السويس", "الشرقية", "شمال سيناء", "جنوب سيناء", "بني سويف", "المنيا", "الفيوم", "أسيوط",
        "الوادي الجديد", "سوهاج", "قنا", "الأقصر", "أسوان", "البحر الأحمر")
 private   val toolbarTitle = MutableLiveData<String>()
    private  val callBack = MutableLiveData<Boolean>()
    private val gender = listOf("النوع", "ذكر", "أنثى")
    private lateinit var db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        binding.lToolBar.title=toolbarTitle.apply { this.value=resources.getString(R.string.register) }
        binding.lToolBar.callback=callBack
        val view = binding.root
        setContentView(view)
        initialization()
    }
    private fun initialization() {
        FirebaseApp.initializeApp(this)
        sharedPreference = SharedPreference(this)
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
        observe(callBack){a-> if (a==true) onBackPressed()}

    }

    fun register(view: View)
    {
        var valid: String = Validation.registerValidation(
            binding.phone.text.toString(),
            binding.name.text.toString(),
            binding.provinceSpinner.selectedItemPosition,
            binding.age.text.toString(),
            binding.genderSpinner.selectedItemPosition
            )
        if (valid == "valid")
        {
            dialog?.show()
            user = User(binding.name.text.toString(),
                binding.phone.text.toString(), province[binding.provinceSpinner.selectedItemPosition],
                binding.age.text.toString(), gender[binding.genderSpinner.selectedItemPosition])

            db.collection("users").document(getDeviceUniqueFootPrint()).set(user)
                .addOnSuccessListener {
                    Toast.makeText(this, R.string.register_success, Toast.LENGTH_SHORT).show()
                    sharedPreference.setIsLogin(true)
                    dialog?.dismiss()
                    finish()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, R.string.register_failed, Toast.LENGTH_SHORT).show()
                    dialog?.dismiss()
                }
        }
        else{
            Toast.makeText(this, valid, Toast.LENGTH_SHORT).show()
        }
    }


}
