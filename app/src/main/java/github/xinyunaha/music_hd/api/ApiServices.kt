package github.xinyunaha.music_hd.api

import android.database.Observable
import github.xinyunaha.music_hd.bean.checkMusic.checkMusic
import github.xinyunaha.music_hd.bean.login.login
import github.xinyunaha.music_hd.bean.search.search
import github.xinyunaha.music_hd.bean.searchSuggest.searchSuggest

import retrofit2.Call
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    /*
     * 登录
     * @query phone 手机号
     * @query md5_password 密码
     * */
    @GET("/login/cellphone")
    fun loginPhone(@Query("phone") username: String,
                   @Query("md5_password") md5_password: String):Call<login>

    /*
     * 搜索
     * @query keywords 关键词
     * @query limit 分页 (可选)
     * @query type 搜索类型 (可选)
     * */
    @GET("search")
    fun search(@Query("keywords") keywords: String):Call<search>

    /*
     * 搜索建议
     * @query keywords 关键词
     * @query type 类型 // mobile=移动端数据
     * */
    @GET("/search/suggest")
    fun searchSuggest(@Query("keywords") keywords: String):Call<searchSuggest>

    /*
         * 歌曲可用性
         * @query id 歌曲ID
         * */
    @GET("/check/music")
    fun checkMusic(@Query("id") id: String):Call<checkMusic>


}