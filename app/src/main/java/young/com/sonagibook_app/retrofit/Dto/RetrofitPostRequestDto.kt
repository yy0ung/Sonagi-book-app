package young.com.sonagibook_app.retrofit.Dto

import com.google.gson.annotations.SerializedName

data class RetrofitPostRequestDto(
    @SerializedName("token")
    val token : String
)
