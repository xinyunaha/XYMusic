package github.xinyunaha.music_hd.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import github.xinyunaha.music_hd.R
import github.xinyunaha.music_hd.moudles.Toast

class SongSearch(val Number:Int, val ID: Long, val Name:String, val Singer:String, val Album:String, val Time:String)


class SongSearchAdapter(private val songList: List<SongSearch>) : RecyclerView.Adapter<SongSearchAdapter.ViewHolder>(){
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val songNumber: TextView = view.findViewById(R.id.search_song_number)
        val songName: TextView = view.findViewById(R.id.search_song_name)
        val songSinger:TextView = view.findViewById(R.id.search_song_singer)
        val songAlbum: TextView = view.findViewById(R.id.search_song_album)
        val songTime: TextView = view.findViewById(R.id.search_song_time)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search, parent, false)
        val viewHolder = ViewHolder(view)
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            val song = songList[position]
            Toast.toast(parent.context,"歌曲ID ${song.ID}")
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val song = songList[position]
        holder.songName.text = song.Name
        holder.songAlbum.text = song.Album
        holder.songNumber.text = (song.Number + 1).toString()
        holder.songSinger.text = song.Singer
        holder.songTime.text = song.Time
    }

    override fun getItemCount() = songList.size
}
