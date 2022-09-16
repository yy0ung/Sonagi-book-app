package young.com.sonagibook_app

import retrofit2.Response
import young.com.sonagibook_app.Utils.API
import young.com.sonagibook_app.retrofit.Dto.RetrofitGetResponseAllInfo
import young.com.sonagibook_app.retrofit.RetrofitClient
import young.com.sonagibook_app.retrofit.RetrofitInterface

class Repository {
    companion object{
        val instance = Repository()
    }
    private val iRetrofit : RetrofitInterface = RetrofitClient.getClient(API.BASE_URL)!!.create(
        RetrofitInterface::class.java)

    suspend fun getAccessToken(token : String) : Response<RetrofitGetResponseAllInfo>{
        return iRetrofit.getAccessToken(token)
    }

}