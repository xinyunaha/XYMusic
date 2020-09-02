package github.xinyunaha.music_hd.bean.login

data class Account(
    val anonimousUser: Boolean,
    val ban: Int,
    val baoyueVersion: Int,
    val createTime: Long,
    val donateVersion: Int,
    val id: Int,
    val salt: String,
    val status: Int,
    val tokenVersion: Int,
    val type: Int,
    val userName: String,
    val vipType: Int,
    val viptypeVersion: Long,
    val whitelistAuthority: Int
)