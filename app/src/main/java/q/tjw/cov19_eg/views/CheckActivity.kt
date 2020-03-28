package q.tjw.cov19_eg.views

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import q.tjw.cov19_eg.R
import q.tjw.cov19_eg.databinding.ActivityCheckBinding

class CheckActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheckBinding
    private val check = listOf(0, 2, 2, 1, 0, 1, 4, 3, 2, 5)
    private var cont: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initialization()
    }

    private fun initialization() {
    }


    fun check(view: View) {
        cont = 0
        if (binding.checkbox1.isChecked){
            cont+= check[1]
        }
        if (binding.checkbox2.isChecked){
            cont+= check[2]
        }
        if (binding.checkbox3.isChecked){
            cont+= check[3]
        }
        if (binding.checkbox4.isChecked){
            cont+= check[4]
        }
        if (binding.checkbox5.isChecked){
            cont+= check[5]
        }
        if (binding.checkbox6.isChecked){
            cont+= check[6]
        }
        if (binding.checkbox7.isChecked){
            cont+= check[7]
        }
        if (binding.checkbox8.isChecked){
            cont+= check[8]
        }
        if (binding.checkbox9.isChecked){
            cont+= check[9]
        }

        when {
            cont == 0 -> {
                showResult(resources.getString(R.string.you_are_ok))
            }
            cont <= 4 -> {
                showResult(resources.getString(R.string.self_isolation))
            }
            cont == 5 -> {
                showResult(resources.getString(R.string.call_doctor))
            }
            else -> {
                showResult(resources.getString(R.string.call_hospital))
            }
        }
    }

    private fun showResult(result: String){
        val dialog = Dialog(this)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.custom_dialog)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // set the custom dialog components - text, image and button
        val ok: Button = dialog.findViewById(R.id.ok)
        val title: TextView = dialog.findViewById(R.id.title)
        title.text = result
        dialog.show()
        ok.setOnClickListener { dialog.dismiss() }


    }


}
