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
import young.com.sonagibook_app.retrofit.Dto.RetrofitMoreInfoPostDto
import young.com.sonagibook_app.retrofit.LoginRepository
import young.com.sonagibook_app.retrofit.dataDto.dataDtoMoreInfo

class LoginInputInfoFragment : Fragment() {
    private lateinit var viewModel : LoginViewModel
    private lateinit var viewModelFactory: LoginViewModelFactory
    private lateinit var registerToken : String
    private lateinit var code : String
    private lateinit var userName : String
    private lateinit var userGrade : String
    private lateinit var userSession : String
    private lateinit var info : RetrofitMoreInfoPostDto

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login_input_info, container, false)
        val name : TextView = view.findViewById(R.id.loginInputUserName)
        val gradeSession : TextView = view.findViewById(R.id.loginInputUserGrade_Session)
        val phone = view.findViewById<EditText>(R.id.loginInputInputPhone)
        val tPhone = phone.text
        val major : EditText = view.findViewById(R.id.loginInputInputMajor)
        val tMajor = major.text
        val birth : EditText = view.findViewById(R.id.loginInputInputBirth)
        val tbirth = birth.text
        val profile : EditText = view.findViewById(R.id.loginInputInputProfile)
        val tProfile = profile.text
        val btn : Button = view.findViewById(R.id.loginInputSaveBtn)
        val map = LinkedHashMap<String, Any>()
        val map2 = LinkedHashMap<String,String?>()


        viewModel = ViewModelProvider(requireActivity(), LoginViewModelFactory(LoginRepository())).get(
            LoginViewModel::class.java)

        CoroutineScope(Dispatchers.Main).launch {
            getLastInfo(viewModel)
            registerToken = viewModel.loginModel.get(0).data.register_token.toString()
            code = viewModel.loginModel2.get(0).inviteCode.toString()
            userName = viewModel.loginModel2.get(0).data?.name.toString()
            userGrade = viewModel.loginModel2.get(0).data?.grade.toString()
            userSession = viewModel.loginModel2.get(0).data?.session.toString()
            name.text = userName
            gradeSession.text = "$userGrade | $userSession"
//
            info = RetrofitMoreInfoPostDto(registerToken,code,
                dataDtoMoreInfo("20000313","01012341234","행정학과",null)
            )

        }

        btn.setOnClickListener {
            lifecycleScope.launch {
                if(tPhone==null || tbirth.isEmpty() || tMajor==null){
                    Toast.makeText(context,"빈칸을 모두 채우세요",Toast.LENGTH_LONG).show()
                }else{

                    postMoreInfo(info)
                    viewModel.loginRepositories3.observe(requireActivity()){
                        Log.d(TAG, "onCreateView: ${it.data}")



                    }
                }

            }
        }

        return view
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


}