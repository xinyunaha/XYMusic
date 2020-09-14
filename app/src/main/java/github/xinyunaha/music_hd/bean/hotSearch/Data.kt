package github.xinyunaha.music_hd.bean.hotSearch

data class Data(
    val alg: String,
    val content: String,
    val iconType: Int,
    val iconUrl: String,
    val score: Int,
    val searchWord: String,
    val source: Int,
    val url: String
)