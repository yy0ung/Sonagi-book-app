package young.com.sonagibook_app.retrofit

import com.google.gson.JsonElement
import org.json.JSONObject
import retrofit2.http.*
import young.com.sonagibook_app.retrofit.Dto.RetrofitMoreInfoPostDto
import young.com.sonagibook_app.retrofit.Dto.RetrofitPostRequestDto
import young.com.sonagibook_app.retrofit.Dto.RetrofitPostResponseDto
import young.com.sonagibook_app.retrofit.Dto.RetrofitUserInfoGetDto

interface RetrofitInterface {

    @POST("https://dding6829.iptime.org:8080/v1/auth/login/kakao")
    fun setPostToken(@Body accesstoken : RetrofitPostRequestDto)
    : retrofit2.Call<RetrofitPostResponseDto>

    @GET("v1/auth/invitation/{code}")
    fun getToken(@Path("code") code : String) : retrofit2.Call<RetrofitUserInfoGetDto>

    @POST("v1/auth/register")
    fun setPostMoreInfo(@Body userInfo : RetrofitMoreInfoPostDto)
    : retrofit2.Call<RetrofitPostResponseDto>
}