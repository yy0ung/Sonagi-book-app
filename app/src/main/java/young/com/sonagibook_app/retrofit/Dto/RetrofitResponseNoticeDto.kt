package young.com.sonagibook_app.retrofit.Dto

import com.google.gson.annotations.SerializedName
import young.com.sonagibook_app.retrofit.dataDto.dataDtoNestedUser

data class RetrofitResponseNoticeDto(
    val data : List<DataDto>
)

data class RetrofitResponseNoticeContentDto(
    val data : DataDto
)

//event 안넣는지? writer 로 바꾸기
data class DataDto(
    @SerializedName("linkedEvent")
    val event : EventNestedDto?,
    @SerializedName("writter")
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

data class EventNestedDto(
    val content : String?,
    val end : String?,
    val place : String?,
    val start : String?,
    val title : String?,
    val type : Int?
)

