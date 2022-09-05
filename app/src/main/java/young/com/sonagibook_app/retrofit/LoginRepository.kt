package young.com.sonagibook_app.retrofit

import young.com.sonagibook_app.Utils.API
import young.com.sonagibook_app.retrofit.Dto.RetrofitPostRequestDto


class LoginRepository {
    companion object{
        val instance = LoginRepository()

    }
    private val iRetrofit : RetrofitInterface = RetrofitClient.getClient(API.BASE_URL)!!.create(RetrofitInterface::class.java)

    suspend fun postToken(token : RetrofitPostRequestDto) =
        iRetrofit.postToken(token)

}