package github.xinyunaha.music_hd.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import github.xinyunaha.music_hd.R
import github.xinyunaha.music_hd.adapter.HotSearch
import github.xinyunaha.music_hd.adapter.HotSearchAdapter
import github.xinyunaha.music_hd.adapter.SongSearch
import github.xinyunaha.music_hd.adapter.SongSearchAdapter
import github.xinyunaha.music_hd.api.ApiEngine
import github.xinyunaha.music_hd.api.ApiService
import github.xinyunaha.music_hd.bean.hotSearch.hotSearch
import github.xinyunaha.music_hd.bean.search.search
import github.xinyunaha.music_hd.moudles.Toast
import github.xinyunaha.music_hd.moudles.formatTime
import kotlinx.android.synthetic.main.fragment_search.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        find_test.setOnClickListener{
//            find_test.text = "fuck"
//            Toast.toast(activity,"Fuck")
//        }

        val request = ApiEngine.retrofitCreate(activity!!.baseContext).create(ApiService::class.java)

        // 热搜界面
        request.hotSearch().enqueue(object  : Callback<hotSearch>{
            var hotSearchList = ArrayList<HotSearch>()
            override fun onResponse(call: Call<hotSearch>, response: Response<hotSearch>) {
                d("热搜加载","成功")
                for (i in response.body()!!.data.indices){
                    val data = response.body()!!.data[i]
                    hotSearchList.add(HotSearch(Number = i,Title = data.searchWord,Content = data.content,Score = data.score))
                    val layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
                    HotSearchList.layoutManager = layoutManager
                    val adapter =HotSearchAdapter(hotSearchList)
                    HotSearchList.adapter = adapter
                }

            }

            override fun onFailure(call: Call<hotSearch>, t: Throwable) {
                Toast.toast(activity,"网络访问失败")
                Toast.toast(activity,"$call")
                Log.e("网络请求", "查询登录状态失败 Error:${t}")
            }

        })


        // 搜索控件监听
        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            // 提交搜索
            override fun onQueryTextSubmit(query: String?): Boolean {
                val songList = ArrayList<SongSearch>()
                // 请求搜索结果
                request.search("$query").enqueue(object :Callback<search>{
                    // 网络请求失败
                    override fun onFailure(call: Call<search>, t: Throwable) {
                        Toast.toast(activity,"网络访问失败")
                        Toast.toast(activity,"$call")
                        Log.e("网络请求", "查询登录状态失败 Error:${t}")
                    }
                    // 网络请求成功
                    override fun onResponse(call: Call<search>, response: Response<search>) {
                        Toast.toast(activity, response.body()?.result!!.songs[1].name)
                        d("搜索结果数量","${response.body()!!.result.songs.size}")
                        for (i in response.body()!!.result.songs.indices){
                            val songs = response.body()!!.result.songs[i]
                            songList.add(SongSearch(Number=i,ID=songs.id.toLong(),Name=songs.name,Singer=songs.artists[0].name,Album=songs.album.name,Time= formatTime(songs.duration)!!))
                            d("结果输出","Number=$i,ID=${songs.id.toLong()},Name=${songs.name},Singer=${songs.artists[0].name},Album=${songs.album.name},Time=${formatTime(songs.duration)}")
                            val layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
                            searchList.layoutManager = layoutManager
                            val adapter =SongSearchAdapter(songList)
                            searchList.adapter = adapter

                        }
                    }
                })
                Toast.toast(activity,"提交了 $query")
                search_view.isIconified = true
                return true
            }
            // 输入字符变动监听
            override fun onQueryTextChange(newText: String?): Boolean {
                Toast.toast(activity,"$newText")
                return false
            }
        })

    }
}