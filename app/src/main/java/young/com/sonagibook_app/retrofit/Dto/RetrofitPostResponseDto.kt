package young.com.sonagibook_app.retrofit.Dto

import young.com.sonagibook_app.retrofit.dataDto.dataDtoToken


data class RetrofitPostResponseDto(
    val success : Boolean,
    val msg : String,
    val data : dataDtoToken,
    val code : Int

)
