package young.com.sonagibook_app.retrofit

import android.content.ContentValues.TAG
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import young.com.sonagibook_app.Utils.API
import young.com.sonagibook_app.retrofit.Dto.RetrofitPostRequestDto
import young.com.sonagibook_app.retrofit.Dto.RetrofitPostResponseDto


class RetrofitManager {
    companion object{
        val instance = RetrofitManager()

    }
    private val iRetrofit : RetrofitInterface?
        = RetrofitClient.getClient(API.BASE_URL)?.create(RetrofitInterface::class.java)

    fun setPostToken(accessToken : RetrofitPostRequestDto){
        val call = iRetrofit?.setPostToken(accessToken)?:return

        call.enqueue(object : retrofit2.Callback<RetrofitPostResponseDto> {
            override fun onFailure(call: Call<RetrofitPostResponseDto>, t: Throwable) {
                Log.d(TAG, "onFailure: fail, ${t.message.toString()}")
            }

            override fun onResponse(
                call: Call<RetrofitPostResponseDto>,
                response: Response<RetrofitPostResponseDto>
            ) {
                Log.d(TAG, "onResponse: success, ${response.body()}")
            }


        })
    }
}