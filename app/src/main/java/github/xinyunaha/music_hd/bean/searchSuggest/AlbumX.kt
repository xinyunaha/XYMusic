package github.xinyunaha.music_hd.bean.searchSuggest

data class AlbumX(
    val artist: ArtistX,
    val copyrightId: Int,
    val id: Int,
    val mark: Int,
    val name: String,
    val picId: Long,
    val publishTime: Long,
    val size: Int,
    val status: Int
)