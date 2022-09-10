package young.com.sonagibook_app

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.launch
import young.com.sonagibook_app.databinding.ActivityMainBinding
import young.com.sonagibook_app.retrofit.Dto.RetrofitPostRequestDto
import young.com.sonagibook_app.retrofit.LoginRepository

class LoginStartFragment : Fragment() {
    private var token = ""

    private lateinit var viewModel: LoginViewModel
    private lateinit var viewModelFactory: LoginViewModelFactory
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login_start, container,false)
        val homeLoginKakao : ImageView = view.findViewById(R.id.homeLoginKakao)


        viewModel = ViewModelProvider(requireActivity(),LoginViewModelFactory(LoginRepository())).get(LoginViewModel::class.java)

        homeLoginKakao.setOnClickListener{
            lifecycleScope.launch {
                try {
                    val oAuthToken = context?.let { it1 -> UserApiClient.loginWithKakao(context = it1) }
                    token = oAuthToken!!.accessToken.toString()
                    setPostToken(token)
                    viewModel.loginRepositories1.observe(requireActivity()){
                        Log.d(ContentValues.TAG, "setPostToken: ${it}")
                        if(it.data.registered == true){
                            (activity as LoginActivity).click(it)
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
        viewModelFactory = LoginViewModelFactory(LoginRepository())
        viewModel = ViewModelProvider(this,viewModelFactory).get(LoginViewModel::class.java)
        val accessToken = RetrofitPostRequestDto(token = token)
        viewModel.postToken(token = accessToken)

    }
}