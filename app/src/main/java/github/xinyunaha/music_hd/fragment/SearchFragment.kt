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
import com.google.gson.Gson
import github.xinyunaha.music_hd.R
import github.xinyunaha.music_hd.api.ApiEngine
import github.xinyunaha.music_hd.api.ApiService
import github.xinyunaha.music_hd.bean.search.search
import github.xinyunaha.music_hd.moudles.Toast
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

        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                // 请求搜索结果
                request.search("$query").enqueue(object :Callback<search>{
                    override fun onFailure(call: Call<search>, t: Throwable) {
                        Toast.toast(activity,"网络访问失败")
                        Toast.toast(activity,"$call")
                        Log.e("网络请求", "查询登录状态失败 Error:${t}")
                    }

                    override fun onResponse(call: Call<search>, response: Response<search>) {
                        Toast.toast(activity, response.body()?.result!!.songs[1].name)
                        d("搜索结果","${response.body()}")
                    }
                })
                Toast.toast(activity,"提交了 $query")
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Toast.toast(activity,"$newText")
                return false
            }
        })

    }
}