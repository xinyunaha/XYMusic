@file:Suppress("DEPRECATION")

package github.xinyunaha.music_hd


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log.d
import android.util.Log.e
import android.view.WindowManager
import android.widget.SeekBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.*
import github.xinyunaha.music_hd.api.ApiEngine.Companion.retrofitCreate
import github.xinyunaha.music_hd.api.ApiService
import github.xinyunaha.music_hd.bean.loginPhone.loginPhone
import github.xinyunaha.music_hd.fragment.*
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import github.xinyunaha.music_hd.bean.loginStatus.loginStatus
import github.xinyunaha.music_hd.moudles.*
import kotlinx.android.synthetic.main.dialog_login.view.*
import kotlin.concurrent.thread

open class MainActivity : AppCompatActivity() {
    @SuppressLint("ShowToast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @Suppress("DEPRECATION")
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_main)

        // 发请求的玩意
        val request = retrofitCreate(this).create(ApiService::class.java)

        nickName_text.isSelected = true
        MusicName_text.isSelected = true
        MusicSinger_text.isSelected = true

        userPic.setImageResource(R.drawable.ic_baseline_sentiment_satisfied_alt_24)
        songPic.setImageResource(R.drawable.ic_baseline_cancel_presentation_24)

        // 登录框
        fun loginDialog(){
            val loginForm = layoutInflater.inflate(R.layout.dialog_login,null)
            AlertDialog.Builder(this)
                .setTitle("登录")
                .setView(loginForm)
                .setPositiveButton("登录"){_,_->
                    thread {
                        val phone = loginForm.dialogPhone.text.toString()
                        val password = loginForm.dialogPassword.text.toString().md5()
                        thread {
                            request.loginPhone(phone, password).enqueue(object: Callback<loginPhone>{
                                override fun onResponse(call: Call<loginPhone>, response: Response<loginPhone>) {
                                    if(response.body()?.code == 502){
                                        Toast.toast(this@MainActivity,"密码错误")
                                    }else{
                                        d("登录状态","登录成功")
                                        Toast.toast(this@MainActivity,"登录成功")
                                        // ui 更新(用户头像及昵称)
                                        Thread{
                                            val bitmap = Picture.getBitmap(response.body()?.profile?.avatarUrl)
                                            val roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(resources, bitmap)
                                            roundedBitmapDrawable.isCircular = true //设置为圆形图片
                                            runOnUiThread {
                                                userPic.setImageDrawable(roundedBitmapDrawable)
                                                nickName_text.text = response.body()?.profile?.nickname
                                            }
                                        }.start()
                                    }
                                }
                                override fun onFailure(call: Call<loginPhone>, t: Throwable) {
                                    Toast.toast(this@MainActivity,"网络访问失败")
                                    e("网络请求","登录失败 Error:${t}")
                                }
                            })
                        }
                    }
                }
                .setNegativeButton("取消"){ _, _ ->
                    Toast.toast(this,"取消登录")
                }
                .create().show()
        }

        // 获取登录情况
        request.loginStatus().enqueue(object :Callback<loginStatus>{
            override fun onResponse(call: Call<loginStatus>, response: Response<loginStatus>) {
                d("登录状态","${response.code()}")
                if (response.code()==301){
                    Toast.toast(this@MainActivity,"需要登录")
                }else{
                    d("登录状态","登录成功")
                    // ui 更新(用户头像及昵称)
                    Thread{
                        val bitmap = Picture.getBitmap(response.body()?.profile?.avatarUrl)
                        val roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(resources, bitmap)
                        roundedBitmapDrawable.isCircular = true //设置为圆形图片
                        runOnUiThread {
                            userPic.setImageDrawable(roundedBitmapDrawable)
                            nickName_text.text = response.body()?.profile?.nickname
                        }
                    }.start()
                }
            }
            override fun onFailure(call: Call<loginStatus>, t: Throwable) {
                Toast.toast(this@MainActivity,"网络访问失败")
                Toast.toast(this@MainActivity,"${call}")
                e("网络请求","查询登录状态失败 Error:${t}")
            }
        })

        // 点击头像弹登录框
        userPic.setOnClickListener {
            loginDialog()
        }



//        Thread{
//            userPic.setImageBitmap(Picture.getBitmap("http://p3.music.126.net/nlicAI5PP9BLwj06eaRfrQ==/109951163365971285.jpg"))
//        }.start()
        // 圆角图片
//        Thread{
//            val bitmap = Picture.getBitmap("http://p3.music.126.net/nlicAI5PP9BLwj06eaRfrQ==/109951163365971285.jpg")
//            val roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(resources, bitmap)
//            roundedBitmapDrawable.isCircular = true //设置为圆形图片
//            runOnUiThread {
//                userPic.setImageDrawable(roundedBitmapDrawable)
//            }
//        }.start()


        val audioPlayer = AudioPlayer(this)
        audioPlayer.ready()
        d("播放列表数量","${audioPlayer.concatenatingMediaSource.size}")

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


//        // 发起HTTP请求
//        request.checkMusic("33894312").enqueue(object : Callback<checkMusic>{
//            override fun onFailure(call: Call<checkMusic>, t: Throwable) {
//                e("网络访问","失败")
//                e("网络访问","$t")
//            }
//            override fun onResponse(call: Call<checkMusic>,response: Response<checkMusic>) {
//                d("网络访问","成功")
//                d("歌曲可用性","${response.body()?.success}")
//                audioPlayer.player.prepare(audioPlayer.concatenatingMediaSource)
//            }
//        })


        setting_img.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.fragment,SettingFragment()).addToBackStack(null).commit()
        }
        search_img.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.fragment,SearchFragment()).addToBackStack(null).commit()
        }
        main_img.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.fragment,MainFragment()).addToBackStack(null).commit()
        }
        user_img.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.fragment,UserFragment()).addToBackStack(null).commit()
        }


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
