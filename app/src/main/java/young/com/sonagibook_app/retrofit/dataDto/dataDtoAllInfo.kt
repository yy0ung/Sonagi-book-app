package young.com.sonagibook_app.retrofit.dataDto

import com.google.gson.annotations.SerializedName

data class dataDtoAllInfo(
    val name : String,
    val grade : Int,
    val session : String,
    @SerializedName("birth")
    val birth : String,
    @SerializedName("major")
    val major : String,
    @SerializedName("phone")
    val phone : String,
    @SerializedName("profileMessage")
    val profileMessage : String?,
    val uid : Int
)
