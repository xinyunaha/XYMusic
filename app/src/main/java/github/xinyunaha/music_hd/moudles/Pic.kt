package github.xinyunaha.music_hd.moudles

import android.R
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.core.graphics.drawable.RoundedBitmapDrawable
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import java.io.BufferedInputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLConnection


class Picture {
    companion object {
        fun getBitmap(url: String?): Bitmap? {
            var bm: Bitmap? = null
            try {
                val iconUrl = URL(url)
                val conn: URLConnection = iconUrl.openConnection()
                val http = conn as HttpURLConnection
                val length = http.contentLength
                conn.connect()
                // 获得图像的字符流
                val stream: InputStream = conn.getInputStream()
                val bis = BufferedInputStream(stream, length)
                bm = BitmapFactory.decodeStream(bis)
                bis.close()
                stream.close() // 关闭流
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("Bitmap转换", "$e")
            }
            return bm
        }
    }
}


