package young.com.sonagibook_app.retrofit.Dto

data class RetrofitPostBookDto(
    val data : BookDto
)

data class BookDto(
    var title : String,
    var place : Int,
    var start : String,
    var end : String
)