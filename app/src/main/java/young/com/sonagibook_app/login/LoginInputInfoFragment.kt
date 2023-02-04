package young.com.sonagibook_app.login

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import young.com.sonagibook_app.R
import young.com.sonagibook_app.databinding.FragmentLoginInputInfoBinding
import young.com.sonagibook_app.retrofit.Dto.RetrofitMoreInfoPostDto
import young.com.sonagibook_app.retrofit.LoginRepository
import young.com.sonagibook_app.retrofit.dataDto.dataDtoMoreInfo
import young.com.sonagibook_app.room.Token
import young.com.sonagibook_app.room.TokenDatabase

class LoginInputInfoFragment : Fragment() {
    private lateinit var viewModel : LoginViewModel
    private lateinit var viewModelFactory: LoginViewModelFactory
    private lateinit var registerToken : String
    private lateinit var code : String
    private lateinit var userName : String
    private lateinit var userGrade : String
    private lateinit var userSession : String
    private lateinit var info : RetrofitMoreInfoPostDto
    private val tokenDB by lazy { TokenDatabase.getInstance(requireActivity()) }
    private var _binding : FragmentLoginInputInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginInputInfoBinding.inflate(layoutInflater)
        val view = binding.root

        viewModel = ViewModelProvider(requireActivity(), LoginViewModelFactory(LoginRepository())).get(
            LoginViewModel::class.java)

        CoroutineScope(Dispatchers.Main).launch {
            getLastInfo(viewModel)
            //accessToken = viewModel.loginModel.get(0).data.access_token.toString()
            registerToken = viewModel.loginModel.get(0).data.register_token.toString()
            code = viewModel.loginModel2.get(0).inviteCode.toString()
            userName = viewModel.loginModel2.get(0).data?.name.toString()
            userGrade = viewModel.loginModel2.get(0).data?.grade.toString()
            userSession = viewModel.loginModel2.get(0).data?.session.toString()
            binding.loginInputUserName.text = userName
            binding.loginInputUserGradeSession.text = "$userGrade | $userSession"
        }

        binding.loginInputSaveBtn.setOnClickListener {
            lifecycleScope.launch {
                if(binding.loginInputInputPhone.text.toString().isEmpty() || binding.loginInputInputBirth.text.toString().isEmpty()
                    || binding.loginInputInputMajor.text.toString().isEmpty()){
                    Toast.makeText(context,"빈칸을 모두 채우세요",Toast.LENGTH_LONG).show()

                }else{
                    inputInfo()
                }

            }
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun inputInfo(){
        info = RetrofitMoreInfoPostDto(registerToken,code, dataDtoMoreInfo(binding.loginInputInputBirth.text.toString(),
                binding.loginInputInputPhone.text.toString(), binding.loginInputInputMajor.text.toString(), binding.loginInputInputProfile.text.toString()))
        Log.d(TAG, "inputInfo: @@@@@@@@$info")
        postMoreInfo(info)
        viewModel.loginRepositories3.observe(requireActivity()){
            Log.d(TAG, "////onCreateView: ${it.data}")
            CoroutineScope(Dispatchers.IO).launch { dbInsert(it.data.access_token.toString(), it.data.refresh_token.toString()) }

        }


    }

    private fun postMoreInfo(userInfo : RetrofitMoreInfoPostDto){
        viewModelFactory = LoginViewModelFactory(LoginRepository())
        viewModel = ViewModelProvider(this,viewModelFactory).get(LoginViewModel::class.java)

        viewModel.postMoreInfo(userInfo)
    }

    private suspend fun getLastInfo(model : LoginViewModel){
        withContext(Dispatchers.IO){
            while(model.loginModel2.size==0 || model.loginModel.size==0){}
        }
    }

    private suspend fun dbInsert(access : String, refresh : String){
        withContext(Dispatchers.IO){
            tokenDB?.tokenDao()?.insert(Token(access, refresh))
        }
    }


}