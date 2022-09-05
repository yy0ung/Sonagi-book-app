package young.com.sonagibook_app.retrofit

import com.google.gson.JsonElement
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.*
import young.com.sonagibook_app.retrofit.Dto.*

interface RetrofitInterface {

    @POST("v1/auth/login/kakao")
    @Headers("Content-type: application/json")
    suspend fun postToken(@Body token : RetrofitPostRequestDto)
    : Response<RetrofitPostResponseDto>

    @GET("v1/auth/invitation/{code}")
    @Headers("Content-type: application/json")
    fun getToken(@Path("code") code : String,
                 @Header("register_token") token : String) : retrofit2.Call<RetrofitUserInfoGetDto>

    @POST("v1/auth/register")
    @Headers("Content-type: application/json")
    fun setPostMoreInfo(@Body userInfo : HashMap<String, Any>)
    : retrofit2.Call<RetrofitPostResponseDto>
}