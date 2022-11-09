package young.com.sonagibook_app.retrofit.Dto

import com.google.gson.annotations.SerializedName
import young.com.sonagibook_app.retrofit.dataDto.dataDtoNestedUser

data class RetrofitResponseNoticeDto(
    val data : List<DataDto>
)

data class RetrofitResponseNoticeContentDto(
    val data : DataDto
)

data class DataDto(
    @SerializedName("Event")
    val event : EventDto?,
    @SerializedName("User")
    val user : dataDtoNestedUser,
    val content : String?,
    val createdAt : String,
    val nid : String,
    val title : String,
    val important : Any?,
    val updatedAt : String,
    val likes : Int,
    val liked : Boolean?
)

data class EventDto(
    val content : String?,
    val end : String?,
    val place : String?,
    val start : String?,
    val title : String?,
    val type : Int?
)

