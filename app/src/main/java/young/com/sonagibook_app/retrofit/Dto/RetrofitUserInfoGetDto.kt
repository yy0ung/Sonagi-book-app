package young.com.sonagibook_app.retrofit.Dto

import young.com.sonagibook_app.retrofit.dataDto.dataDtoServerUserInfo

data class RetrofitUserInfoGetDto(
    val success : Boolean,
    val msg : String,
    val data : dataDtoServerUserInfo?,
    val code : Int?
)
