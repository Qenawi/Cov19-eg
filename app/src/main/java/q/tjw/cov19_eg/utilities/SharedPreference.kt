package q.tjw.cov19_eg.utilities

import android.content.Context
import android.content.SharedPreferences

class SharedPreference(val context: Context) {
    private val MY_PREFS = "MY_PREFS"
    private val IS_LOGIN = "IS_LOGIN"
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE)

    fun setIsLogin(isLogin: Boolean) {
        sharedPreferences.edit().putBoolean(IS_LOGIN, isLogin).apply()
    }

    fun getIsLogin(): Boolean? {
        return sharedPreferences.getBoolean(IS_LOGIN, false)
    }


    fun clearSharedPreference() {
        sharedPreferences.edit().clear().apply()
    }

}