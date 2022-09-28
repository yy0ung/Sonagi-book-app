package young.com.sonagibook_app.retrofit.Dto

data class RetrofitResponseNoticeLikeDto(
    val success : Boolean,
    val msg : String,
    val data : NoticeLikeDto?
)

data class NoticeLikeDto(
    val likes : Int
)
