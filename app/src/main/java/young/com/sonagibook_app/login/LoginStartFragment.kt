package young.com.sonagibook_app.login

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import young.com.sonagibook_app.*
import young.com.sonagibook_app.retrofit.Dto.RetrofitPostRequestDto
import young.com.sonagibook_app.retrofit.LoginRepository
import young.com.sonagibook_app.room.Token
import young.com.sonagibook_app.room.TokenDatabase

class LoginStartFragment : Fragment() {
    private var token = ""
    private val tokenDB by lazy { TokenDatabase.getInstance(requireActivity()) }
    private lateinit var viewModel: LoginViewModel
    private lateinit var loginViewModelFactory: LoginViewModelFactory
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login_start, container,false)
        val homeLoginKakao : ImageView = view.findViewById(R.id.loginStartKakaoBtn)



        viewModel = ViewModelProvider(requireActivity(), LoginViewModelFactory(LoginRepository())).get(
            LoginViewModel::class.java)



        homeLoginKakao.setOnClickListener{
            lifecycleScope.launch {
                try {
                    val oAuthToken = context?.let { it1 -> UserApiClient.loginWithKakao(context = it1) }
                    token = oAuthToken!!.accessToken.toString()
                    setPostToken(token)
                    viewModel.loginRepositories1.observe(requireActivity()){
                        Log.d(ContentValues.TAG, "////setPostToken: ${it}")
                        if(it.data.registered == true){
                            //CoroutineScope(Dispatchers.Main).launch { dbInsert(it.data.access_token.toString(), it.data.refresh_token.toString()) }
                            Toast.makeText(context,"이미 로그인 된 계정",Toast.LENGTH_LONG).show()
                            val intent = Intent(context, MainActivity::class.java)
                            intent.putExtra("accessToken", it.data.access_token.toString())
                            startActivity(intent)
                        }else if(it.data.registered == false){
//                            val intent = Intent(context,HomeActivity2::class.java)
//                            startActivity(intent)
                            (activity as LoginActivity).click(it)
                        }
                    }

                } catch (error: Throwable) {
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        Log.d("MainActivity", "사용자가 취소")
                    } else {
                        Log.e("MainActivity", "인증 에러", error)
                    }
                }
            }
        }




        return view
    }

    private suspend fun setPostToken(token : String){
        loginViewModelFactory = LoginViewModelFactory(LoginRepository())
        viewModel = ViewModelProvider(this,loginViewModelFactory).get(LoginViewModel::class.java)
        val accessToken = RetrofitPostRequestDto(token = token)
        viewModel.postToken(token = accessToken)

    }
    private suspend fun dbInsert(access : String, refresh : String){
        withContext(Dispatchers.IO){ tokenDB?.tokenDao()?.insert(Token(access, refresh)) }
    }


}