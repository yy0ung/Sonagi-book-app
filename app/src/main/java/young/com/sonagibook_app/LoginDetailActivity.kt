package young.com.sonagibook_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import young.com.sonagibook_app.databinding.ActivityLoginDetailBinding
import young.com.sonagibook_app.retrofit.Dto.RetrofitPostRequestDto
import young.com.sonagibook_app.retrofit.RetrofitManager

class LoginDetailActivity : AppCompatActivity() {
    lateinit var binding : ActivityLoginDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val token : String? = intent.getStringExtra("token").toString()
        binding.loginToken.text = token

        binding.loginAPIBtn.setOnClickListener {
            val value = RetrofitPostRequestDto(token!!)
            val retrofit = RetrofitManager.instance.setPostToken(value)
        }
    }
}