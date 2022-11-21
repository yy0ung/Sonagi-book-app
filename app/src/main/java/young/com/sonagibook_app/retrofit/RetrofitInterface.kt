package young.com.sonagibook_app.retrofit

import com.google.gson.JsonElement
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.*
import young.com.sonagibook_app.retrofit.Dto.*

interface RetrofitInterface {

    //login
    @POST("v1/auth/login/kakao")
    @Headers("Content-type: application/json")
    suspend fun postToken(@Body token : RetrofitPostRequestDto)
    : Response<RetrofitPostResponseDto>

    @GET("v1/auth/invitation/{code}")
    @Headers("Content-type: application/json")
    suspend fun getToken(@Path("code") code : String,
                 @Header("register_token") token : String) : Response<RetrofitUserInfoGetDto>

    @POST("v1/auth/register")
    @Headers("Content-type: application/json")
    suspend fun postMoreInfo(@Body userInfo : RetrofitMoreInfoPostDto)
    : Response<RetrofitPostResponseDto>

    //auth
    @GET("v1/auth/token")
    @Headers("Content-type: application/json")
    suspend fun getAccessToken(@Header("Authorization") token : String)
    : Response<RetrofitGetResponseAllInfo>

    @POST("v1/auth/token")
    @Headers("Content-type: application/json")
    suspend fun postRefreshToken(@Body refreshToken : HashMap<String,String>) : Response<RetrofitResponseRefreshTokenDto>

    //notice
    @POST("v1/notice")
    @Headers("Content-type: application/json")
    suspend fun postNewNotice(@Header("Authorization") token : String,
    @Body noticeInfo : RetrofitPostNoticeDto)

    @GET("v1/notice/list/{page}")
    @Headers("Content-type: application/json")
    suspend fun getNoticeList(@Path("page")page : Int,
                              @Header("Authorization") token : String)
    :Response<RetrofitResponseNoticeDto>

    @GET("v1/notice/{nid}")
    @Headers("Content-type: application/json")
    suspend fun getNoticeContent(@Path("nid") nid : String, @Header("Authorization") token : String)
            :Response<RetrofitResponseNoticeContentDto>

    @DELETE("v1/notice/{nid}")
    @Headers("Content-type: application/json")
    suspend fun deleteNoticeItem(@Path("nid") nid : String, @Header("Authorization") token : String)

    @POST("v1/notice/like")
    @Headers("Content-type: application/json")
    suspend fun postNoticeLike(@Header("Authorization") token : String, @Body nid : RetrofitPostNoticeLikeDto)
            : Response<RetrofitResponseNoticeLikeDto>

    @POST("v1/notice/unlike")
    @Headers("Content-type: application/json")
    suspend fun postNoticeCancelLike(@Header("Authorization") token : String, @Body nid : RetrofitPostNoticeLikeDto)
            : Response<RetrofitResponseNoticeLikeDto>

    @PUT("v1/notice/{nid}")
    @Headers("Content-type: application/json")
    suspend fun putNoticeContent(@Path("nid") nid : String,
                                 @Header("Authorization") token : String, @Body data : RetrofitPostNoticeDto)

    //schedule
    @POST("v1/event")
    @Headers("Content-type: application/json")
    suspend fun postSchedule(@Header("Authorization") token : String, @Body data : RetrofitPostScheduleDto)


    @GET("v1/event/list/{date}")
    @Headers("Content-type: application/json")
    suspend fun getScheduleList(@Header("Authorization") token : String, @Path("date") date : String) : Response<RetrofitResponseScheduleDto>

    @GET("v1/event/{eid}")
    @Headers("Content-type: application/json")
    suspend fun getScheduleContent(@Path("eid") eid : String, @Header("Authorization") token : String)
            :Response<RetrofitResponseScheduleContentDto>

    @DELETE("v1/event/{eid}")
    @Headers("Content-type: application/json")
    suspend fun deleteScheduleItem(@Path("eid") eid : String, @Header("Authorization") token : String)

    @PUT("v1/event/{eid}")
    @Headers("Content-type: application/json")
    suspend fun putScheduleContent(@Path("eid") eid : String,
                                 @Header("Authorization") token : String, @Body data : RetrofitPostScheduleDto)

    //book
    @POST("v1/reservation")
    @Headers("Content-type: application/json")
    suspend fun postBook(@Header("Authorization") token : String, @Body data : RetrofitPostBookDto)

    @GET("v1/reservation/list/{date}")
    @Headers("Content-type: application/json")
    suspend fun getBookList(@Path("date") date : String, @Header("Authorization") token : String)
            :Response<RetrofitResponseBookDto>

}