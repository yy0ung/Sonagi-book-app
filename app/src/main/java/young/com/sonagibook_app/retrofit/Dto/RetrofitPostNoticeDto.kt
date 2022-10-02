package young.com.sonagibook_app.retrofit.Dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class RetrofitPostNoticeDto(
    @SerializedName("data")
    var data : NoticeDto?
)

data class RetrofitPostNoticeLikeDto(
    @SerializedName("data")
    val data : HashMap<String, String>
)

data class NoticeDto(
    @SerializedName("title")
    var title: String?=null,
    @SerializedName("content")
    var content: String?=null,
    @SerializedName("important")
    var important : Any? = null
)
