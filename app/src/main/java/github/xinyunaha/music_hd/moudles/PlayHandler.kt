package github.xinyunaha.music_hd.moudles

import android.os.Handler
import android.os.Message
import android.widget.SeekBar
import com.google.android.exoplayer2.ExoPlayer
import java.lang.ref.WeakReference

class PlayHandler(seekBar: SeekBar, player: ExoPlayer): Handler(){
    private val seekBar = WeakReference<SeekBar>(seekBar)
    private val players = WeakReference<ExoPlayer>(player)

    override fun handleMessage(msg: Message) {
        super.handleMessage(msg)
        when (msg.what){
            0 ->{
                seekBar.get()?.max = players.get()?.duration!!.toInt()
                seekBar.get()?.progress = players.get()!!.currentPosition.toInt()
                sendEmptyMessageDelayed(0,300)
            }
        }
    }
}