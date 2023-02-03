package ca.coffeeshopstudio.meksheets.data

import android.content.Context
import android.content.SharedPreferences

private const val DARK_MODE: String = "DARK_MODE"
private const val DYNAMIC_MODE: String = "DYNAMIC_MODE"

class PrefManager(context: Context) {
    private val sharedPreference: SharedPreferences

    init {
        sharedPreference = context.getSharedPreferences("USER_PREFS", Context.MODE_PRIVATE)
    }

    fun darkMode(): Int {
        return sharedPreference.getInt(DARK_MODE, 0)
    }

    fun dynamicMode(): Boolean {
        return sharedPreference.getBoolean(DYNAMIC_MODE, false)
    }

    fun setDarkMode(mode: Int) {
        val editor = sharedPreference.edit()
        editor.putInt(DARK_MODE, mode)
        editor.apply()
    }

    fun setDynamicMode(mode: Boolean) {
        val editor = sharedPreference.edit()
        editor.putBoolean(DYNAMIC_MODE, mode)
        editor.apply()
    }
}