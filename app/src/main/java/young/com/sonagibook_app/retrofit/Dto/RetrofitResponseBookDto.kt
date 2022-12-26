package young.com.sonagibook_app.retrofit.Dto

import com.google.gson.annotations.SerializedName
import young.com.sonagibook_app.retrofit.dataDto.dataDtoNestedUser

data class RetrofitResponseBookDto(
    val data : List<ResponseBookDto>
)

//date type : "2022-11-27T13:00:00.000Z"
data class ResponseBookDto(
    @SerializedName("writter")
    val user : dataDtoNestedUser,
    val title : String,
    val content : String,
    val place : Int,
    val start : String,
    val end : String,
    val rid : Int,
    val createdAt : String,
    val updatedAt : String
)
