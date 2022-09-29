package young.com.sonagibook_app.retrofit.Dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

class RetrofitPostNoticeDto(
    @SerializedName("data")
    var data : NoticeDto?
)

class RetrofitPostNoticeLikeDto(
    @SerializedName("data")
    val data : HashMap<String, String>
)

class NoticeDto(
    @SerializedName("title")
    var title : String?=null,
    @SerializedName("content")
    var content : String?=null,
    @SerializedName("important")
    var important : Boolean?=null
)
