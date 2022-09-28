package young.com.sonagibook_app.retrofit.Dto

import com.google.gson.annotations.SerializedName

data class RetrofitResponseNoticeDto(
    val data : List<DataDto>
)

data class DataDto(
    @SerializedName("User.name")
    val name : String,
    @SerializedName("User.privileged")
    val privileged : Int,
    @SerializedName("User.session")
    val session : String,
    val createdAt : String,
    val nid : String,
    val title : String,
    val important : Any?,
    val updatedAt : String,
    val likes : Int
)
