package young.com.sonagibook_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import young.com.sonagibook_app.databinding.ActivityLoginDetailBinding

class LoginDetailActivity : AppCompatActivity() {
    lateinit var binding : ActivityLoginDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val token : String? = intent.getStringExtra("token").toString()
        binding.loginToken.text = token
    }
}