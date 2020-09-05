package github.xinyunaha.music_hd.moudles

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast


object Toast {
    private var myToast: Toast? = null

    @SuppressLint("ShowToast")
    fun toast(context: Context?, text: String?) {
        myToast = if (myToast != null) {
            myToast!!.cancel()
            Toast.makeText(context, text, Toast.LENGTH_SHORT)
        } else {
            Toast.makeText(context, text, Toast.LENGTH_SHORT)
        }
        myToast?.show()
    }

    @SuppressLint("ShowToast")
    fun toastLong(context: Context?, text: String?) {
        myToast = if (myToast != null) {
            myToast!!.cancel()
            Toast.makeText(context, text, Toast.LENGTH_LONG)
        } else {
            Toast.makeText(context, text, Toast.LENGTH_LONG)
        }
        myToast?.show()
    }
}