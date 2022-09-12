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



    }


}