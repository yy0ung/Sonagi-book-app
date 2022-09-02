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
import kotlinx.coroutines.launch
import young.com.sonagibook_app.databinding.ActivityMainBinding


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
                    val intent = Intent(applicationContext,LoginDetailActivity::class.java)
                    intent.putExtra("token", oAuthToken.accessToken.toString())

                    Log.d("MainActivity", "#### ${oAuthToken.accessToken}")
                    startActivity(intent)
                } catch (error: Throwable) {
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        Log.d("MainActivity", "사용자가 취소")
                    } else {
                        Log.e("MainActivity", "인증 에러", error)
                    }
                }
            }
        }

//        binding.homeLoginToken.setOnClickListener {
//            Toast.makeText(this,"${token}",Toast.LENGTH_SHORT).show()
//        }


    }
}