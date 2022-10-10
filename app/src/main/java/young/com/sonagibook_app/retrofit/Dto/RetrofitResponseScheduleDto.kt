package young.com.sonagibook_app.retrofit.Dto

import com.google.gson.annotations.SerializedName

data class RetrofitResponseScheduleDto(
    val data : List<ScheduleDto>
)

data class ScheduleResponseDto(
    @SerializedName("User.name")
    val name : String,
    @SerializedName("User.privileged")
    val privileged : Int,
    @SerializedName("User.session")
    val session : String,
    val content : String,
    val createdAt : String,
    val eid : Int,
    val end : String,
    val place : String,
    val repeatDat : Int?,
    val start : String,
    val title : String,
    val updatedAt : String
)
