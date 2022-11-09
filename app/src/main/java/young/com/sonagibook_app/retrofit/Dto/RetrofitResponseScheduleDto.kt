package young.com.sonagibook_app.retrofit.Dto

import com.google.gson.annotations.SerializedName
import young.com.sonagibook_app.retrofit.dataDto.dataDtoNestedUser

data class RetrofitResponseScheduleDto(
    val data : List<ScheduleDto>
)

data class ScheduleResponseDto(
    @SerializedName("User")
    val user : dataDtoNestedUser,
    @SerializedName("Notice")
    val notice : NoticeNestedDto?,
    val content : String,
    val createdAt : String,
    val eid : Int,
    val end : String,
    val place : String,
    val repeatDay : Int?,
    val start : String,
    val title : String,
    val nid : Int?,
    val type : Int?,
    val updatedAt : String
)

data class NoticeNestedDto(
    val content : String?,
    val createdAt : String?,
    val nid : Int?,
    val title : String?,
    val updatedAt: String?

)
