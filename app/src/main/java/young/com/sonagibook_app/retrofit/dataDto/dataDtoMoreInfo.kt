package young.com.sonagibook_app.retrofit.dataDto

import com.google.gson.annotations.SerializedName

data class dataDtoMoreInfo(
    @SerializedName("birth")
    var birth : String,
    @SerializedName("phone")
    var phone : String,
    @SerializedName("major")
    var major : String,
    @SerializedName("profile_message")
    var profile_message : String?
)
