package github.xinyunaha.music_hd.bean.loginPhone

data class loginPhone(
    val account: Account,
    val bindings: List<Binding>,
    val code: Int,
    val cookie: String,
    val loginType: Int,
    val profile: Profile,
    val token: String
)