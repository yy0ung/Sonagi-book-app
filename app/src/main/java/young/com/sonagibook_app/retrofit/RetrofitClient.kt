package young.com.sonagibook_app.retrofit

import android.content.ContentValues.TAG
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.internal.GsonBuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

//retrofit client 선언
object RetrofitClient {
    private var retrofitClient : Retrofit? = null

    fun getClient(baseUrl : String) : Retrofit?{
        Log.d(TAG, "getClient: called")

        val client = OkHttpClient.Builder()
        val loggingInterceptor = HttpLoggingInterceptor(object :HttpLoggingInterceptor.Logger{
            override fun log(message: String) {
                Log.d(TAG, "log: message ${message.toString()}")
            }
        })
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        client.addInterceptor(loggingInterceptor)

        if(retrofitClient == null){
            retrofitClient = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client.build())

                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofitClient
    }
}