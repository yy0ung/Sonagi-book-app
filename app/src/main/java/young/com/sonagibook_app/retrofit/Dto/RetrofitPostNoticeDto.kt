package young.com.sonagibook_app.retrofit.Dto

import com.google.gson.annotations.SerializedName

data class RetrofitPostNoticeDto(
    @SerializedName("title")
    val title : String,
    @SerializedName("content")
    val content : String,
    @SerializedName("important")
    val important : Any
)
