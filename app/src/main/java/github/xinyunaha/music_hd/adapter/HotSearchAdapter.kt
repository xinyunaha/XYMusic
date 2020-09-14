package github.xinyunaha.music_hd.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import github.xinyunaha.music_hd.R
import github.xinyunaha.music_hd.moudles.Toast

class HotSearch (val Number:Int, val Title: String, val Content:String, val Score:Int)

class HotSearchAdapter(private val hotList: List<HotSearch>) : RecyclerView.Adapter<HotSearchAdapter.ViewHolder>(){
    inner class ViewHolder(view: View) :RecyclerView.ViewHolder(view){
        val hotNumber:TextView = view.findViewById(R.id.search_hot_number)
        val hotTitle:TextView = view.findViewById(R.id.search_hot_title)
        val hotContent:TextView = view.findViewById(R.id.search_hot_content)
        val hotSore:TextView = view.findViewById(R.id.search_hot_score)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.item_hot_search,parent,false)
        val viewHolder = ViewHolder(view)
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            val title = hotList[position]
            Toast.toast(parent.context,"搜索 ${title.Title}")
        }
        return  viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val hot = hotList[position]
        holder.hotTitle.text = hot.Title
        holder.hotNumber.text = (hot.Number + 1).toString()
        holder.hotContent.text = hot.Content
        holder.hotSore.text = hot.Score.toString()
    }

    override fun getItemCount() = hotList.size

}