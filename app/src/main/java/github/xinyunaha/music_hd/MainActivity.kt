@file:Suppress("DEPRECATION")

package github.xinyunaha.music_hd


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log.d
import android.util.Log.e
import android.view.WindowManager
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.*
import github.xinyunaha.music_hd.api.ApiEngine.Companion.retrofitCreate
import github.xinyunaha.music_hd.api.ApiService
import github.xinyunaha.music_hd.bean.checkMusic.checkMusic
import github.xinyunaha.music_hd.fragment.*
import github.xinyunaha.music_hd.moudles.AudioPlayer
import github.xinyunaha.music_hd.moudles.PlayHandler
import github.xinyunaha.music_hd.moudles.Toast
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


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

        // 开局跳转发现界面
        supportFragmentManager.beginTransaction().replace(R.id.fragment,SettingFragment()).addToBackStack(null).commit()

        val audioPlayer = AudioPlayer(this)
        audioPlayer.addSourceList("http://m7.music.126.net/20200905130030/e205dd0d2abcb597d0c6d10b955ac6c5/ymusic/76e5/ba34/d562/2e95d6640354faee9ef0d6a384d2bc5f.mp3")

        d("播放列表数量","${audioPlayer.concatenatingMediaSource.size}")
        audioPlayer.player.playWhenReady = true

        // Exoplayer 进度条绑定
        val playHandlers = PlayHandler(seekBar, audioPlayer.player)
        audioPlayer.concatenatingMediaSource.addEventListener(playHandlers, object : DefaultMediaSourceEventListener() {
            override fun onLoadStarted(windowIndex: Int, mediaPeriodId: MediaSource.MediaPeriodId?, loadEventInfo: MediaSourceEventListener.LoadEventInfo?, mediaLoadData: MediaSourceEventListener.MediaLoadData?) {
                super.onLoadStarted(windowIndex, mediaPeriodId, loadEventInfo, mediaLoadData)
                seekBar.max = audioPlayer.player.duration.toInt()
                playHandlers.sendEmptyMessage(0)
            }
        })

        audioPlayer.player.addListener(object : Player.EventListener {
            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                when (playbackState) {
                    Player.STATE_IDLE -> {
                        d("播放状态", "STATE_IDLE")
                        Toast.toast(this@MainActivity,"播放出错：${audioPlayer.player.playbackError}")
                    }
                    Player.STATE_BUFFERING -> {
                        d("播放状态", "STATE_BUFFERING")
                    }
                    Player.STATE_READY -> {
                        d("播放状态", "STATE_READY")
                    }
                    Player.STATE_ENDED -> {
                        d("播放状态", "STATE_ENDED")
                        Toast.toast(this@MainActivity,"没有更多了")
                        runOnUiThread { stop_play_btn.setImageResource(R.drawable.exo_controls_play)
                            seekBar.progress = 0
                        }
                    }
                }
            }
        })

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                audioPlayer.player.seekTo(seekBar?.progress!!.toLong())
            }
        })

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
                audioPlayer.player.prepare(audioPlayer.concatenatingMediaSource)
            }
        })

//        find_btn.setOnClickListener {
//            supportFragmentManager.beginTransaction().replace(R.id.fragment,FindFragment()).addToBackStack(null).commit()
//        }

        //停止播放监听
        stop_play_btn.setOnClickListener {
            d("播放控制","正在播放：${audioPlayer.player.playWhenReady})")
            if(audioPlayer.status()){
                audioPlayer.stop()
                stop_play_btn.setImageResource(R.drawable.exo_controls_play)
            }else{
                if(audioPlayer.player.playbackState == Player.STATE_ENDED){
                    Toast.toast(this,"没有更多了")
                    audioPlayer.stop()
                }else {
                    audioPlayer.start()
                    if (audioPlayer.player.playbackError == null) {
                        stop_play_btn.setImageResource(R.drawable.exo_controls_pause)
                    }
                }
            }


        }
    }
}
