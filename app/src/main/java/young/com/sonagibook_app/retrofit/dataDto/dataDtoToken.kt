package young.com.sonagibook_app.retrofit.dataDto

import com.google.gson.annotations.SerializedName

data class dataDtoToken(
    @SerializedName("registered")
    val registered : Boolean?,
    @SerializedName("access_token")
    val access_token : String?,
    @SerializedName("refresh_token")
    val refresh_token : String?,
    @SerializedName("register_token")
    val register_token : String?
)
