package q.tjw.cov19_eg.utilities

import q.tjw.cov19_eg.R

object Validation {

    fun registerValidation(mobile: String?, name: String?, province: Int, age: String?): String {
        return when {
            name == "" -> {
                App.context?.resources?.getString(R.string.invalid_name)!!
            }
            mobileValidation(mobile) != "valid" -> {
                App.context?.resources?.getString(R.string.invalid_phone_number)!!
            }
            age == "" -> {
                return App.context?.resources?.getString(R.string.invalid_name)!!
            }
            province == 0 -> {
                App.context?.resources?.getString(R.string.invalid_province)!!
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
                App.context?.resources?.getString(R.string.invalid_phone_number)
            } else {
                "valid"
            }
        } else {
            App.context?.resources
                ?.getString(R.string.invalid_phone_number)
        }
    }

}