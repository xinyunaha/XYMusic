package github.xinyunaha.music_hd.api

import android.content.Context
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ApiEngine {
    companion object {
        // 网络拦截器
        private fun okHttpClient(context: Context): OkHttpClient {
            //cookies持久化
            val cookieJar = PersistentCookieJar(
                SetCookieCache(),
                SharedPrefsCookiePersistor(context.applicationContext)
            )
            return OkHttpClient.Builder().apply {
                cookieJar(cookieJar)
            }.build()
        }

         fun retrofitCreate(context: Context): Retrofit {
            return Retrofit.Builder().apply {
                baseUrl("http://192.168.0.107:55555/")
                addConverterFactory(GsonConverterFactory.create())
                client(okHttpClient(context))
//                this
            }.run {
                build()
            }
        }

    }
}