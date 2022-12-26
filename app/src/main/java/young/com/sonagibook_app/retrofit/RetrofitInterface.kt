package young.com.sonagibook_app.retrofit

import com.google.gson.JsonElement
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.*
import young.com.sonagibook_app.retrofit.Dto.*

interface RetrofitInterface {

    //login
    @POST("auth/login/kakao")
    @Headers("Content-type: application/json")
    suspend fun postToken(@Body token : RetrofitPostRequestDto)
    : Response<RetrofitPostResponseDto>

    @GET("auth/invitation/{code}")
    @Headers("Content-type: application/json")
    suspend fun getToken(@Path("code") code : String,
                 @Header("register_token") token : String) : Response<RetrofitUserInfoGetDto>

    @POST("auth/register")
    @Headers("Content-type: application/json")
    suspend fun postMoreInfo(@Body userInfo : RetrofitMoreInfoPostDto)
    : Response<RetrofitPostResponseDto>

    //auth
    @GET("auth/token")
    @Headers("Content-type: application/json")
    suspend fun getAccessToken(@Header("Authorization") token : String)
    : Response<RetrofitGetResponseAllInfo>

    @POST("auth/token")
    @Headers("Content-type: application/json")
    suspend fun postRefreshToken(@Header("Authorization") token : String, @Body refreshToken : HashMap<String,String>) : Response<RetrofitResponseRefreshTokenDto>

    //notice
    @POST("notice")
    @Headers("Content-type: application/json")
    suspend fun postNewNotice(@Header("Authorization") token : String,
    @Body noticeInfo : RetrofitPostNoticeDto)

    @GET("notice/list/{page}")
    @Headers("Content-type: application/json")
    suspend fun getNoticeList(@Path("page")page : Int,
                              @Header("Authorization") token : String)
    :Response<RetrofitResponseNoticeDto>

    @GET("notice/{nid}")
    @Headers("Content-type: application/json")
    suspend fun getNoticeContent(@Path("nid") nid : String, @Header("Authorization") token : String)
            :Response<RetrofitResponseNoticeContentDto>

    @DELETE("notice/{nid}")
    @Headers("Content-type: application/json")
    suspend fun deleteNoticeItem(@Path("nid") nid : String, @Header("Authorization") token : String)

    @POST("notice/like")
    @Headers("Content-type: application/json")
    suspend fun postNoticeLike(@Header("Authorization") token : String, @Body nid : RetrofitPostNoticeLikeDto)
            : Response<RetrofitResponseNoticeLikeDto>

    @POST("notice/unlike")
    @Headers("Content-type: application/json")
    suspend fun postNoticeCancelLike(@Header("Authorization") token : String, @Body nid : RetrofitPostNoticeLikeDto)
            : Response<RetrofitResponseNoticeLikeDto>

    @PUT("notice/{nid}")
    @Headers("Content-type: application/json")
    suspend fun putNoticeContent(@Path("nid") nid : String,
                                 @Header("Authorization") token : String, @Body data : RetrofitPostNoticeDto)

    //schedule
    @POST("event")
    @Headers("Content-type: application/json")
    suspend fun postSchedule(@Header("Authorization") token : String, @Body data : RetrofitPostScheduleDto)


    @GET("event/list/{date}")
    @Headers("Content-type: application/json")
    //date => YYYY-MM
    suspend fun getScheduleList(@Header("Authorization") token : String, @Path("date") date : String) : Response<RetrofitResponseScheduleDto>

    @GET("event/{eid}")
    @Headers("Content-type: application/json")
    suspend fun getScheduleContent(@Path("eid") eid : String, @Header("Authorization") token : String)
            :Response<RetrofitResponseScheduleContentDto>

    @DELETE("event/{eid}")
    @Headers("Content-type: application/json")
    suspend fun deleteScheduleItem(@Path("eid") eid : String, @Header("Authorization") token : String)

    @PUT("event/{eid}")
    @Headers("Content-type: application/json")
    suspend fun putScheduleContent(@Path("eid") eid : String,
                                 @Header("Authorization") token : String, @Body data : RetrofitPostScheduleDto)

    //book
    @POST("reservation")
    @Headers("Content-type: application/json")
    suspend fun postBook(@Header("Authorization") token : String, @Body data : RetrofitPostBookDto)

    @GET("reservation/list/{date}")
    @Headers("Content-type: application/json")
    //date type: 'YYYY-MM-DD'
    suspend fun getBookList(@Path("date") date : String, @Header("Authorization") token : String)
            :Response<RetrofitResponseBookDto>

}