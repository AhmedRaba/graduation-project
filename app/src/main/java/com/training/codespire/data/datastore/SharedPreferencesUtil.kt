import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesUtil(context: Context) {

    companion object {
        private const val PREFS_FILENAME = "MyPrefs"
        private const val KEY_IS_LOGGED_IN = "isLoggedIn"
    }

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)

    var isLoggedIn: Boolean
        get() = sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
        set(value) = sharedPreferences.edit().putBoolean(KEY_IS_LOGGED_IN, value).apply()

    fun clear() {
        sharedPreferences.edit().remove(KEY_IS_LOGGED_IN).apply()
    }
}
