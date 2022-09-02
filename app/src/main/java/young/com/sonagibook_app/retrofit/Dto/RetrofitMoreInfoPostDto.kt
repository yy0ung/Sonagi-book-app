package young.com.sonagibook_app.retrofit.Dto

import young.com.sonagibook_app.retrofit.dataDto.dataDtoMoreInfo

data class RetrofitMoreInfoPostDto(
    val register_token : String,
    val code : String,
    val data : dataDtoMoreInfo
)
