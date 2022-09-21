package young.com.sonagibook_app.login

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.WindowManager
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import young.com.sonagibook_app.*
import young.com.sonagibook_app.retrofit.Dto.RetrofitPostResponseDto
import young.com.sonagibook_app.retrofit.Dto.RetrofitUserInfoGetDto
import young.com.sonagibook_app.retrofit.LoginRepository
import young.com.sonagibook_app.room.RoomRepository
import young.com.sonagibook_app.room.Token
import young.com.sonagibook_app.room.TokenDatabase


class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: LoginViewModel
    private lateinit var viewModelFactory: LoginViewModelFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        viewModel = ViewModelProvider(this, LoginViewModelFactory(LoginRepository())).get(
            LoginViewModel::class.java)

        val tokenDB by lazy { TokenDatabase.getInstance(this) }

        CoroutineScope(Dispatchers.IO).launch {
            //tokenDB?.tokenDao()?.insert(Token("aa","bb"))
            val rst = tokenDB?.tokenDao()?.getAll()
            Log.d(TAG, "onCreate: ####${rst?.accessToken}")
        }




        val fragment = supportFragmentManager.beginTransaction()
        fragment.add(R.id.fragment, LoginStartFragment()).commit()
    }
    fun click(obj : RetrofitPostResponseDto){
        viewModel.loginModel.add(obj)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment, LoginCodeInputFragment()).commit()

    }
    fun click2(obj : RetrofitUserInfoGetDto){
        viewModel.loginModel2.add(obj)
//        Log.d(TAG, "click2: ${viewModel.loginModel2.size}")
//        Log.d(TAG, "click2: ${viewModel.loginModel2.get(0).data}")
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment, LoginInputInfoFragment()).commit()
    }
}