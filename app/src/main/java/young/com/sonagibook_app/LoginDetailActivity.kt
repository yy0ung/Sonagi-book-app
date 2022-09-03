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
import young.com.sonagibook_app.databinding.ActivityLoginDetailBinding
import young.com.sonagibook_app.retrofit.Dto.RetrofitPostRequestDto
import young.com.sonagibook_app.retrofit.Dto.RetrofitUserInfoGetDto
import young.com.sonagibook_app.retrofit.RetrofitClient
import young.com.sonagibook_app.retrofit.RetrofitInterface
import young.com.sonagibook_app.retrofit.RetrofitManager

class LoginDetailActivity : AppCompatActivity() {
    lateinit var binding : ActivityLoginDetailBinding
    lateinit var registerToken : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val inviteCode = binding.loginInvitationCode

        registerToken = intent.getStringExtra("registerToken").toString()

        Toast.makeText(this, registerToken,Toast.LENGTH_SHORT).show()
        binding.loginCodeProofBtn.setOnClickListener {
            getToken(inviteCode.text.toString(), registerToken.toString())
        }

    }

    private fun getToken(code : String, token : String){
        val iRetrofit : RetrofitInterface = RetrofitClient.getClient(API.BASE_URL)!!.create(
            RetrofitInterface::class.java)

        val call = iRetrofit.getToken(code, token)
        call.enqueue(object : retrofit2.Callback<RetrofitUserInfoGetDto>{
            override fun onResponse(
                call: Call<RetrofitUserInfoGetDto>,
                response: Response<RetrofitUserInfoGetDto>
            ) {
                Log.d(TAG, "onResponse: ${response.body()}")
                if(response.isSuccessful){
                    val intent = Intent(this@LoginDetailActivity,LoginInputActivity::class.java)

                    intent.putExtra("registerToken", registerToken.toString())
                    intent.putExtra("code", binding.loginInvitationCode.text.toString())
                    startActivity(intent)
                }
            }

            override fun onFailure(call: Call<RetrofitUserInfoGetDto>, t: Throwable) {
                Log.d(TAG, "onFailure: fail, $t")
            }

        })
    }
}