package github.xinyunaha.music_hd.bean.searchSuggest

data class Song(
    val album: AlbumX,
    val alias: List<String>,
    val artists: List<ArtistXX>,
    val copyrightId: Int,
    val duration: Int,
    val fee: Int,
    val ftype: Int,
    val id: Int,
    val mark: Int,
    val mvid: Int,
    val name: String,
    val rUrl: Any,
    val rtype: Int,
    val status: Int
)