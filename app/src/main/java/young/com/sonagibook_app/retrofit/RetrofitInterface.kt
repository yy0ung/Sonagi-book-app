package young.com.sonagibook_app.retrofit

import com.google.gson.JsonElement
import org.json.JSONObject
import retrofit2.http.*
import young.com.sonagibook_app.retrofit.Dto.*

interface RetrofitInterface {

    @POST("v1/auth/login/kakao")
    @Headers("Content-type: application/json")
    fun setPostToken(@Body token : RetrofitPostRequestDto)
    : retrofit2.Call<RetrofitPostResponseDto>

    @GET("v1/auth/invitation/{code}")
    @Headers("Content-type: application/json")
    fun getToken(@Path("code") code : String,
                 @Header("register_token") token : String) : retrofit2.Call<RetrofitUserInfoGetDto>

    @POST("v1/auth/register")
    @Headers("Content-type: application/json")
    fun setPostMoreInfo(@Body userInfo : RetrofitMoreInfoPostDto)
    : retrofit2.Call<RetrofitPostResponseDto>
}