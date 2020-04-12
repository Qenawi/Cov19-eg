package q.tjw.cov19_eg.views

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.provider.Settings.Secure
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import carbon.widget.DropDown
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_register.*
import q.tjw.cov19_eg.R
import q.tjw.cov19_eg.databinding.ActivityRegisterBinding
import q.tjw.cov19_eg.map.core.data.Navigation
import q.tjw.cov19_eg.map.core.extentions.getDeviceUniqueFootPrint
import q.tjw.cov19_eg.map.core.extentions.mVisible
import q.tjw.cov19_eg.map.core.extentions.observe
import q.tjw.cov19_eg.model.User
import q.tjw.cov19_eg.utilities.SharedPreference
import q.tjw.cov19_eg.utilities.Validation


class RegisterActivity : AppCompatActivity() {
companion object{
    val province = arrayListOf<String>( "القاهرة", "الجيزة", "القليوبية", "الإسكندرية", "البحيرة", "مطروح", "الدقهلية", "كفر الشيخ", "الغربية",
        "المنوفية", "دمياط", "بورسعيد", "الإسماعيلية", "السويس", "الشرقية", "شمال سيناء", "جنوب سيناء", "بني سويف", "المنيا", "الفيوم", "أسيوط",
        "الوادي الجديد", "سوهاج", "قنا", "الأقصر", "أسوان", "البحر الأحمر")
    val gender = listOf( "ذكر", "أنثى")
    var open_next: Navigation?=Navigation.Home
}
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var sharedPreference: SharedPreference
    private var provinceAdapter: ArrayAdapter<String>? = null
    private var genderAdapter: ArrayAdapter<String>? = null
    private lateinit var user: User
    private var dialog: ProgressDialog? = null

   private   val toolbarTitle = MutableLiveData<String>()
    private  val callBack = MutableLiveData<Boolean>()

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
        (province_spinner as DropDown<String>).setItems(province)
        (gender_spinner as DropDown<String>).setItems(gender)
        observe(callBack){a-> if (a==true) onBackPressed()}

    }

    fun register(view: View)
    {
        var valid: String = Validation.registerValidation(
            binding.phone.text.toString(),
            binding.name.text.toString(),
            (province_spinner as DropDown<String>).selectedIndex,
            binding.age.text.toString(),
            (gender_spinner as DropDown<String>).selectedIndex
            )
        if (valid == "valid")
        {
            dialog?.show()
            user = User(binding.name.text.toString(),
                binding.phone.text.toString(),(province_spinner as DropDown<String>).selectedItem,
                binding.age.text.toString(), (gender_spinner as DropDown<String>).selectedItem)

             db.collection("users").document(getDeviceUniqueFootPrint()).set(user)
                .addOnSuccessListener {
                    Toast.makeText(this, R.string.register_success, Toast.LENGTH_SHORT).show()
                    sharedPreference.setIsLogin(true)
                    dialog?.dismiss()
                    setResult(Activity.RESULT_OK, Intent())
                    finish()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, R.string.register_failed, Toast.LENGTH_SHORT).show()
                    dialog?.dismiss()
                }
        }
        else{Toast.makeText(this, valid, Toast.LENGTH_SHORT).show()}
    }
}
