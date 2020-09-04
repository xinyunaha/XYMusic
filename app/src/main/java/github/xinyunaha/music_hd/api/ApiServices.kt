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

const val BaseUrl = "https://home.bokro.cn:1443/wyyyy/"

interface ApiService {

    @GET("login/cellphone")
    fun loginPhone(@Query("phone") username: String,
                   @Query("md5_password") md5_password: String):Call<login>

    @GET("search")
    fun search(@Query("keywords") keywords: String):Call<search>

    @GET("search/suggest")
    fun searchSuggest(@Query("keywords") keywords: String):Call<searchSuggest>

    @GET("check/music")
    fun checkMusic(@Query("id") id: String):Call<checkMusic>


}