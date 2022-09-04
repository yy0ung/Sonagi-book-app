package young.com.sonagibook_app

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import retrofit2.Call
import retrofit2.Response
import young.com.sonagibook_app.Utils.API
import young.com.sonagibook_app.databinding.ActivityLoginInputBinding
import young.com.sonagibook_app.retrofit.Dto.RetrofitMoreInfoPostDto
import young.com.sonagibook_app.retrofit.Dto.RetrofitPostResponseDto
import young.com.sonagibook_app.retrofit.RetrofitClient
import young.com.sonagibook_app.retrofit.RetrofitInterface
import young.com.sonagibook_app.retrofit.dataDto.dataDtoMoreInfo

class LoginInputActivity : AppCompatActivity() {
    lateinit var binding : ActivityLoginInputBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginInputBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val token = intent.getStringExtra("registerToken")!!
        val code = intent.getStringExtra("code")!!

        val brith = binding.loginInputBrith
        val phone = binding.loginInputPhone
        val major = binding.loginInputMajor
        val profile = binding.loginInputProfileMsg
        val dataClass = dataDtoMoreInfo(brith.text.toString(), phone.text.toString(),
            major.text.toString(), profile.text.toString())
        val map = LinkedHashMap<String, Any>()
        val map2 = LinkedHashMap<String,String?>()
        map.put("register_token",token)
        map.put("code",code)
        map2.put("birth","20000313")
        map2.put("phone","01012341234")
        map2.put("major","aa")
        map2.put("profile_message","none")
        map.put("data", map2)


        binding.loginInputBtn.setOnClickListener {

            Toast.makeText(this,brith.text.toString(),Toast.LENGTH_LONG).show()
            setPostMoreInfo(map)
        }

    }

    private fun setPostMoreInfo(map : LinkedHashMap<String, Any>){
        val iRetrofit : RetrofitInterface = RetrofitClient.getClient(API.BASE_URL)!!.create(
            RetrofitInterface::class.java)

        //val info = RetrofitMoreInfoPostDto(register_token = token, code = code, data = data)

        val call = iRetrofit.setPostMoreInfo(map)
        call.enqueue(object : retrofit2.Callback<RetrofitPostResponseDto>{
            override fun onResponse(
                call: Call<RetrofitPostResponseDto>,
                response: Response<RetrofitPostResponseDto>
            ) {
                Log.d(TAG, "onResponse: success, ${response.body()}")
//                if(response.isSuccessful){
//                    val intent = Intent(this@LoginInputActivity, HomeActivity2::class.java)
//                    startActivity(intent)
//                }
            }

            override fun onFailure(call: Call<RetrofitPostResponseDto>, t: Throwable) {
                Log.d(TAG, "onFailure: fail, $t")
            }

        })
    }
}