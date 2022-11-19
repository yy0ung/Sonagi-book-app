package young.com.sonagibook_app.retrofit.Dto

import com.google.gson.annotations.SerializedName
import young.com.sonagibook_app.retrofit.dataDto.dataDtoNestedUser

data class RetrofitResponseBookDto(
    @SerializedName("User")
    val user : dataDtoNestedUser,
    val title : String,
    val place : Int,
    val start : String,
    val end : String,
    val rid : Int,
    val createdAt : String,
    val updatedAt : String
)
