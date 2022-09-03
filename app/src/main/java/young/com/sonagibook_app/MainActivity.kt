package young.com.sonagibook_app

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import young.com.sonagibook_app.Utils.API
import young.com.sonagibook_app.databinding.ActivityMainBinding
import young.com.sonagibook_app.retrofit.Dto.RetrofitPostRequestDto
import young.com.sonagibook_app.retrofit.Dto.RetrofitPostResponseDto
import young.com.sonagibook_app.retrofit.RetrofitClient
import young.com.sonagibook_app.retrofit.RetrofitInterface
import young.com.sonagibook_app.retrofit.RetrofitManager
import java.util.ArrayList


class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    var token : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.homeLoginKakao.setOnClickListener{
            lifecycleScope.launch {
                try {
                    val oAuthToken = UserApiClient.loginWithKakao(context = applicationContext)
                    token = oAuthToken.accessToken.toString()
                    setPostToken(accessToken = token)

//                    val intent = Intent(this@MainActivity, LoginDetailActivity::class.java)
//                    startActivity(intent)
                } catch (error: Throwable) {
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        Log.d("MainActivity", "사용자가 취소")
                    } else {
                        Log.e("MainActivity", "인증 에러", error)
                    }
                }
            }
        }





    }

    private fun setPostToken(accessToken : String){
        val iRetrofit : RetrofitInterface = RetrofitClient.getClient(API.BASE_URL)!!.create(RetrofitInterface::class.java)
        val token = RetrofitPostRequestDto(token = accessToken)
        val call = iRetrofit.setPostToken(token = token)

        call.enqueue(object : retrofit2.Callback<RetrofitPostResponseDto> {
            override fun onResponse(
                call: Call<RetrofitPostResponseDto>,
                response: Response<RetrofitPostResponseDto>
            ) {
                Log.d(TAG, "onResponse: success, ${response.body()}")
                if(response.isSuccessful){
                    if(response.body()?.data?.registered == true){
                        val intent = Intent(this@MainActivity,HomeActivity2::class.java)
                        intent.putExtra("registerToken", response.body()?.data?.register_token.toString())
                        startActivity(intent)
                    }else{
                        val intent = Intent(this@MainActivity,LoginDetailActivity::class.java)
                        intent.putExtra("registerToken", response.body()?.data?.register_token.toString())
                        startActivity(intent)
                    }
                }

            }

            override fun onFailure(call: Call<RetrofitPostResponseDto>, t: Throwable) {
                Log.d(TAG, "onFailure: fail, $t")
            }


        })
    }


}