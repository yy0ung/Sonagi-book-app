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

}