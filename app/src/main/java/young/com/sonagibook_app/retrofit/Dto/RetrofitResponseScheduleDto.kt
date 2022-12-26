package young.com.sonagibook_app.retrofit.Dto

import com.google.gson.annotations.SerializedName
import young.com.sonagibook_app.retrofit.dataDto.dataDtoNestedUser

data class RetrofitResponseScheduleDto(
    val data : List<ScheduleResponseDto>
)

data class RetrofitResponseScheduleContentDto(
    val data : ScheduleResponseDto
)

//date type : "2022-11-27T13:00:00.000Z"
data class ScheduleResponseDto(
    @SerializedName("writter")
    val user : dataDtoNestedUser,
    @SerializedName("linkedNotice")
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
