package young.com.sonagibook_app.retrofit

import android.content.ContentValues.TAG
import android.content.Intent
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import young.com.sonagibook_app.LoginInputActivity
import young.com.sonagibook_app.Utils.API
import young.com.sonagibook_app.retrofit.Dto.RetrofitPostRequestDto
import young.com.sonagibook_app.retrofit.Dto.RetrofitPostResponseDto
import java.lang.Exception
import java.util.ArrayList


class RetrofitManager {
    companion object{
        val instance = RetrofitManager()

    }
    private val iRetrofit : RetrofitInterface

        = RetrofitClient.getClient(API.BASE_URL)!!.create(RetrofitInterface::class.java)


}