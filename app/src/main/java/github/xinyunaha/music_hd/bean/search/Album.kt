package github.xinyunaha.music_hd.bean.search

data class Album(
    val alia: List<String>,
    val artist: Artist,
    val copyrightId: Int,
    val id: Int,
    val mark: Int,
    val name: String,
    val picId: Long,
    val publishTime: Long,
    val size: Int,
    val status: Int
)