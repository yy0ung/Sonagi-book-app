package young.com.sonagibook_app.retrofit.Dto

import com.google.gson.annotations.SerializedName
import young.com.sonagibook_app.retrofit.dataDto.dataDtoAllInfo

data class RetrofitGetResponseAllInfo(
    val success : Boolean,
    val msg : String,
    val data : dataDtoAllInfo

)
