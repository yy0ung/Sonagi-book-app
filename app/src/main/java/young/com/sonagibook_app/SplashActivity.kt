package young.com.sonagibook_app

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import young.com.sonagibook_app.login.LoginActivity
import young.com.sonagibook_app.room.Token
import young.com.sonagibook_app.room.TokenDatabase

class SplashActivity : AppCompatActivity() {
    private val tokenDB by lazy { TokenDatabase.getInstance(applicationContext) }
    override fun onRestart() {
        //이부분 수정 필요
        super.onRestart()
        //DB에 token 저장되어있는지 여부에 따라 화면 전환
        CoroutineScope(Dispatchers.Main).launch {
            //tokenDB?.tokenDao()?.insert(Token("aa","bb"))
            val rst =
                withContext(CoroutineScope(Dispatchers.IO).coroutineContext) { getTokenDB() }
            Log.d(TAG, "onCreate: $rst")
            if(rst?.refreshToken==null){
                val intent = Intent(this@SplashActivity,LoginActivity::class.java)
                startActivity(intent)
            }else {
                val intent = Intent(this@SplashActivity,MainActivity::class.java)
                startActivity(intent)
            }

        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        //DB에 token 저장되어있는지 여부에 따라 화면 전환
        CoroutineScope(Dispatchers.Main).launch {
            //tokenDB?.tokenDao()?.insert(Token("aa","bb"))
            val rst =
                withContext(CoroutineScope(Dispatchers.IO).coroutineContext) { getTokenDB() }
            Log.d(TAG, "onCreate: $rst")
            if(rst?.refreshToken==null){
                val intent = Intent(this@SplashActivity,LoginActivity::class.java)
                startActivity(intent)
            }else {
                val intent = Intent(this@SplashActivity,MainActivity::class.java)
                startActivity(intent)
            }

        }
    }
    private suspend fun getTokenDB() : Token?{
        return tokenDB?.tokenDao()?.getAll()
    }


}