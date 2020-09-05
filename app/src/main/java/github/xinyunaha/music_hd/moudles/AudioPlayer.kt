package github.xinyunaha.music_hd.moudles

import android.content.Context
import android.net.Uri
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import github.xinyunaha.music_hd.MainActivity
import github.xinyunaha.music_hd.R
import kotlinx.android.synthetic.main.activity_main.*

@Suppress("DEPRECATION")
class AudioPlayer(context: Context) {
        val player = ExoPlayerFactory.newSimpleInstance(context, DefaultTrackSelector())
        private val defaultDataSourceFactory = DefaultDataSourceFactory(context, "audio/mpeg")
        val concatenatingMediaSource = ConcatenatingMediaSource()

        fun addSourceList(Url: String) {
            val mediaSource = ExtractorMediaSource.Factory(defaultDataSourceFactory)
                .createMediaSource(Uri.parse(Url))
            concatenatingMediaSource.addMediaSource(mediaSource)
        }

        fun stop() {
            player.playWhenReady = false
        }

        fun start() {
            player.playWhenReady = true
        }

        fun status(): Boolean {
            return player.playWhenReady
        }

        fun next() {

        }

    }