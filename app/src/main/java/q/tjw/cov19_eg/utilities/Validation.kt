package q.tjw.cov19_eg.utilities

import q.tjw.cov19_eg.R
import q.tjw.cov19_eg.map.di.app.CO19Application

object Validation {

    fun registerValidation(mobile: String?, name: String?, province: Int, age: String?, gender: Int): String {
        return when {
            name == "" -> {
                CO19Application.context?.resources?.getString(R.string.invalid_name)!!
            }
            mobileValidation(mobile) != "valid" -> {
                CO19Application.context?.resources?.getString(R.string.invalid_phone_number)!!
            }
            age == "" -> {
                return CO19Application.context?.resources?.getString(R.string.invalid_age)!!
            }
            gender == 0 -> {
                CO19Application.context?.resources?.getString(R.string.invalid_gender)!!
            }
            province == 0 -> {
                CO19Application.context?.resources?.getString(R.string.invalid_province)!!
            }
            else -> {
                "valid"
            }
        }
        return "valid"
    }

    private fun mobileValidation(mobile: String?): String? {
        return if (mobile != null) {

            if (!(mobile.length == 11 &&
                        mobile.substring(0, 2) == "01" &&
                        Regex( "[0-9]+").matches(mobile))) {
                CO19Application.context?.resources?.getString(R.string.invalid_phone_number)
            } else {
                "valid"
            }
        } else {
            CO19Application.context?.resources
                ?.getString(R.string.invalid_phone_number)
        }
    }

}