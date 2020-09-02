package github.xinyunaha.music_hd.bean.searchSuggest

data class Result(
    val albums: List<Album>,
    val order: List<String>,
    val songs: List<Song>
)