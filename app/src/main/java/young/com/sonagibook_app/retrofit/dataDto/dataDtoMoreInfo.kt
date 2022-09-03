package young.com.sonagibook_app.retrofit.dataDto

import com.google.gson.annotations.SerializedName

data class dataDtoMoreInfo(
    @SerializedName("brith")
    val brith : String,
    @SerializedName("phone")
    val phone : String,
    @SerializedName("major")
    val major : String,
    @SerializedName("profile_message")
    val profile_message : String?
)
