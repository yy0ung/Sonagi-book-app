package young.com.sonagibook_app.retrofit.Dto

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import young.com.sonagibook_app.retrofit.dataDto.dataDtoToken


data class RetrofitPostResponseDto(
    @SerializedName("success")
    val success : Boolean,
    @SerializedName("msg")
    val msg : String,
    @SerializedName("data")
    val data : dataDtoToken,
    @SerializedName("code")
    val code : Int?

)
