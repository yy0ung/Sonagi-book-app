package young.com.sonagibook_app.retrofit

import android.content.ContentValues.TAG
import android.util.Log
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//retrofit client 선언
object RetrofitClient {
    private var retrofitClient : Retrofit? = null

    fun getClient(baseUrl : String) : Retrofit?{
        Log.d(TAG, "getClient: called")

        if(retrofitClient == null){
            retrofitClient = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofitClient
    }
}