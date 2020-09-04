@file:Suppress("DEPRECATION")

package github.xinyunaha.music_hd


import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log.d
import android.util.Log.e
import android.view.WindowManager
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.source.*
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import github.xinyunaha.music_hd.api.ApiEngine.Companion.retrofitCreate
import github.xinyunaha.music_hd.api.ApiService
import github.xinyunaha.music_hd.bean.checkMusic.checkMusic
import github.xinyunaha.music_hd.fragment.*
import github.xinyunaha.music_hd.util.PlayHandler
import github.xinyunaha.music_hd.util.ToastUtil
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.ref.WeakReference


open class MainActivity : AppCompatActivity() {
    @SuppressLint("ShowToast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @Suppress("DEPRECATION")
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_main)

        nickName_text.isSelected = true
        MusicName_text.isSelected = true
        MusicSinger_text.isSelected = true


        val url = "http://123.147.165.27:8899/m8.music.126.net/20200905003808/400a070b0855dcff4bac405b7cb61692/ymusic/76e5/ba34/d562/2e95d6640354faee9ef0d6a384d2bc5f.mp3"
        val player = ExoPlayerFactory.newSimpleInstance(this@MainActivity, DefaultTrackSelector())
        val defaultDataSourceFactory = DefaultDataSourceFactory(this@MainActivity, "audio/mpeg")
        val concatenatingMediaSource = ConcatenatingMediaSource()
        val mediaSource1 = ExtractorMediaSource.Factory(defaultDataSourceFactory)
            .createMediaSource(Uri.parse(url))
        concatenatingMediaSource.addMediaSource(mediaSource1)
        d("播放列表数量","${concatenatingMediaSource.size}")
        player.playWhenReady = true

        val playHandlers = PlayHandler(seekBar, player)
        concatenatingMediaSource.addEventListener(playHandlers, object : DefaultMediaSourceEventListener() {
            override fun onLoadStarted(windowIndex: Int, mediaPeriodId: MediaSource.MediaPeriodId?, loadEventInfo: MediaSourceEventListener.LoadEventInfo?, mediaLoadData: MediaSourceEventListener.MediaLoadData?) {
                super.onLoadStarted(windowIndex, mediaPeriodId, loadEventInfo, mediaLoadData)
                seekBar.max = player.duration.toInt()
                playHandlers.sendEmptyMessage(0)
            }
        })

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                player.seekTo(seekBar?.progress!!.toLong())
            }

        })
        seekBar.onProg
        // 进度条Handler
        val playHandler = PlayHandler(seekBar, player)

        // 发请求的玩意
        val request = retrofitCreate(this).create(ApiService::class.java)

        // 发起HTTP请求
        request.checkMusic("33894312").enqueue(object : Callback<checkMusic>{
            override fun onFailure(call: Call<checkMusic>, t: Throwable) {
                e("网络访问","失败")
                e("网络访问","$t")
            }
            override fun onResponse(call: Call<checkMusic>,response: Response<checkMusic>) {
                d("网络访问","成功")
                d("歌曲可用性","${response.body()?.success}")
                player.prepare(concatenatingMediaSource)
            }
        })

        // 开局跳转发现界面
        supportFragmentManager.beginTransaction().replace(R.id.fragment,FindFragment()).addToBackStack(null).commit()

//        find_btn.setOnClickListener {
//            supportFragmentManager.beginTransaction().replace(R.id.fragment,FindFragment()).addToBackStack(null).commit()
//        }

        //停止播放监听
        stop_play_btn.setOnClickListener {
            if(player.playWhenReady){
                d("播放控制","暂停")
                player.playWhenReady = false
            }else{
                d("播放控制","播放")
                player.playWhenReady = true
            }


        }
    }
}
