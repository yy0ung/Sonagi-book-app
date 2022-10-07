package young.com.sonagibook_app.retrofit.Dto

import com.google.gson.annotations.SerializedName
import young.com.sonagibook_app.retrofit.dataDto.dataDtoMoreInfo

data class RetrofitMoreInfoPostDto(
    @SerializedName("register_token")
    var register_token : String,
    @SerializedName("code")
    var code : String,
    @SerializedName("data")
    var data : dataDtoMoreInfo
)


