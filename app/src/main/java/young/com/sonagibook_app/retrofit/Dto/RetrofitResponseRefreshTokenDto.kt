package young.com.sonagibook_app.retrofit.Dto

import com.google.gson.annotations.SerializedName

data class RetrofitResponseRefreshTokenDto(
    @SerializedName("success")
    val success : Boolean,
    @SerializedName("msg")
    val msg : String,
    @SerializedName("data")
    val data : TokenDto
)

data class TokenDto(
    @SerializedName("access_token")
    val accessToken : String
)
