package young.com.sonagibook_app.retrofit.Dto

data class RetrofitPostBookDto(
    val data : BookDto
)

//date type : "2022-11-29T17:00:00"
data class BookDto(
    var title : String,
    var content : String,
    var place : Int,
    var start : String,
    var end : String
)