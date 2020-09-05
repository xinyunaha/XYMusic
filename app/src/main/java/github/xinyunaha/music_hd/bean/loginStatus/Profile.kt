package github.xinyunaha.music_hd.bean.loginStatus

data class Profile(
    val avatarUrl: String,
    val birthday: String,
    val djStatus: Int,
    val nickname: String,
    val userId: Int,
    val userType: Int
)