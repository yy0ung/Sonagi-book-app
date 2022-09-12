package young.com.sonagibook_app.retrofit

import retrofit2.Response
import young.com.sonagibook_app.Utils.API
import young.com.sonagibook_app.retrofit.Dto.RetrofitPostRequestDto
import young.com.sonagibook_app.retrofit.Dto.RetrofitPostResponseDto
import young.com.sonagibook_app.retrofit.Dto.RetrofitUserInfoGetDto


class LoginRepository {
    companion object{
        val instance = LoginRepository()

    }
    private val iRetrofit : RetrofitInterface = RetrofitClient.getClient(API.BASE_URL)!!.create(RetrofitInterface::class.java)

    suspend fun postToken(token : RetrofitPostRequestDto) : Response<RetrofitPostResponseDto>{
        return iRetrofit.postToken(token)
    }

    suspend fun getToken(code : String, token : String) : Response<RetrofitUserInfoGetDto>{
        return iRetrofit.getToken(code, token)
    }

    suspend fun postMoreInfo(userInfo : HashMap<String, Any>) : Response<RetrofitPostResponseDto>{
        return iRetrofit.postMoreInfo(userInfo)
    }

}