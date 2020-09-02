package github.xinyunaha.music_hd.bean.search

data class Result(
    val hasMore: Boolean,
    val songCount: Int,
    val songs: List<Song>
)