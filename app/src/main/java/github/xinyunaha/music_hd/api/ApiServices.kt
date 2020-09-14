package github.xinyunaha.music_hd.api

import github.xinyunaha.music_hd.bean.checkMusic.checkMusic
import github.xinyunaha.music_hd.bean.hotSearch.hotSearch
import github.xinyunaha.music_hd.bean.loginOut.loginOut
import github.xinyunaha.music_hd.bean.loginPhone.loginPhone
import github.xinyunaha.music_hd.bean.loginStatus.loginStatus
import github.xinyunaha.music_hd.bean.search.search
import github.xinyunaha.music_hd.bean.searchSuggest.searchSuggest

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

const val BaseUrl = "https://home.bokro.cn:1443/wyyyy/"

interface ApiService {

    @GET("login/cellphone")
    fun loginPhone(@Query("phone") phone: String,
                   @Query("md5_password") md5_password: String
    ):Call<loginPhone>

    @GET("login/status")
    fun loginStatus():Call<loginStatus>

    @GET("logout")
    fun logOut():Call<loginOut>

    @GET("search")
    fun search(@Query("keywords") keywords: String):Call<search>

    @GET("search/suggest")
    fun searchSuggest(@Query("keywords") keywords: String):Call<searchSuggest>

    @GET("check/music")
    fun checkMusic(@Query("id") id: String):Call<checkMusic>

    @GET("search/hot/detail")
    fun hotSearch():Call<hotSearch>


}