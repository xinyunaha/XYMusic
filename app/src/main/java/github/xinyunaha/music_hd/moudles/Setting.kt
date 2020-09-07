package github.xinyunaha.music_hd.moudles

import android.app.Activity
import android.content.Context.MODE_PRIVATE

class Setting {
    companion object {
        fun saveData(context: Activity, key: String, info: String) {
            val sharedPreferences = context.getSharedPreferences(key, MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString(key, info)
            editor.apply()
        }

        fun getData(context: Activity, key: String): String {
            val result = context.getSharedPreferences(key, MODE_PRIVATE).getString(key, "")
            return if (result!!.isEmpty()) {
                ""
            } else {
                result
            }
        }

        fun clearData(context: Activity, key: String) {
            context.getSharedPreferences(key, MODE_PRIVATE).edit().clear().apply()
        }

    }
}