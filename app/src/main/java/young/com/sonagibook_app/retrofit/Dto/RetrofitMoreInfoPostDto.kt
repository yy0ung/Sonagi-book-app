package young.com.sonagibook_app.retrofit.Dto

import com.google.gson.annotations.SerializedName
import young.com.sonagibook_app.retrofit.dataDto.dataDtoMoreInfo

data class RetrofitMoreInfoPostDto(
    @SerializedName("register_token")
    val register_token : String,
    @SerializedName("code")
    val code : String,
    @SerializedName("data")
    val data : dataDtoMoreInfo
)
