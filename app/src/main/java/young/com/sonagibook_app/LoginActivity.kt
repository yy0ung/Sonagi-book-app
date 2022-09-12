package young.com.sonagibook_app

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import young.com.sonagibook_app.retrofit.Dto.RetrofitPostResponseDto
import young.com.sonagibook_app.retrofit.Dto.RetrofitUserInfoGetDto
import young.com.sonagibook_app.retrofit.LoginRepository


class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: LoginViewModel
    private lateinit var viewModelFactory: LoginViewModelFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        viewModel = ViewModelProvider(this, LoginViewModelFactory(LoginRepository())).get(LoginViewModel::class.java)


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