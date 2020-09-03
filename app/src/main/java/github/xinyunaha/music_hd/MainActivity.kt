@file:Suppress("DEPRECATION")

package github.xinyunaha.music_hd

import android.app.Application
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log.d
import android.util.Log.e
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import github.xinyunaha.music_hd.api.ApiEngine.Companion.retrofitCreate
import github.xinyunaha.music_hd.api.ApiService
import github.xinyunaha.music_hd.bean.checkMusic.checkMusic
import retrofit2.Callback


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @Suppress("DEPRECATION")
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_main)

        // 发请求的玩意
        val request = retrofitCreate(this).create(ApiService::class.java)

        // 发起HTTP请求
        request.checkMusic("33894312").enqueue(object : Callback<checkMusic>{
            override fun onFailure(call: retrofit2.Call<checkMusic>, t: Throwable) {
                e("网络访问","失败")
                e("网络访问","$t")
            }
            override fun onResponse(call: retrofit2.Call<checkMusic>,response: retrofit2.Response<checkMusic>) {
                d("网络访问","成功")
                d("歌曲可用性","${response.body()?.success}")

                val url = "http://m8.music.126.net/20200903104437/668eb35edb81ee99a4fd23545f3f8ca3/ymusic/0fd6/4f65/43ed/a8772889f38dfcb91c04da915b301617.mp3"
                val player = ExoPlayerFactory.newSimpleInstance(this@MainActivity, DefaultTrackSelector())
                val defaultDataSourceFactory = DefaultDataSourceFactory(this@MainActivity, "audio/mpeg")
                val concatenatingMediaSource = ConcatenatingMediaSource()
                val mediaSource1 = ExtractorMediaSource.Factory(defaultDataSourceFactory)
                    .createMediaSource(Uri.parse(url))
                concatenatingMediaSource.addMediaSource(mediaSource1)
                player.playWhenReady = true
                player.prepare(concatenatingMediaSource)
            }
        })

    }
}
