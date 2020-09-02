package github.xinyunaha.music_hd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import android.util.Log.e
import github.xinyunaha.music_hd.api.ApiEngine.Companion.retrofitCreate
import github.xinyunaha.music_hd.api.ApiService
import github.xinyunaha.music_hd.bean.checkMusic.checkMusic
import retrofit2.Callback


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val request = retrofitCreate(this).create(ApiService::class.java)

        request.checkMusic("33894312").enqueue(object : Callback<checkMusic>{
            override fun onFailure(call: retrofit2.Call<checkMusic>, t: Throwable) {
                e("网络访问","失败")
                e("网络访问","$t")
            }
            override fun onResponse(call: retrofit2.Call<checkMusic>,response: retrofit2.Response<checkMusic>) {
                d("网络访问","成功")
                d("歌曲可用性","${response.body()?.success}")
            }
        })

    }
}
