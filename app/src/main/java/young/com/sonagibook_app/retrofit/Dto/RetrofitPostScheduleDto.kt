package young.com.sonagibook_app.retrofit.Dto

data class RetrofitPostScheduleDto(
    var data : ScheduleDto
)

data class ScheduleDto(
    var title : String,
    var content : String,
    var place : String,
    var start : String,
    var end : String,
    var repeatDay : Int?
)