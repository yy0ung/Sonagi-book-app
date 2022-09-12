package young.com.sonagibook_app

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login_input_info, container, false)
        val name : TextView = view.findViewById(R.id.loginInputName)
        val grade : TextView = view.findViewById(R.id.loginInputGrade)
        val session : TextView = view.findViewById(R.id.loginInputSession)
        val phone = view.findViewById<EditText>(R.id.loginInputPhone)
        val tPhone = phone.text
        val major : EditText = view.findViewById(R.id.loginInputMajor)
        val tMajor = major.text
        val birth : EditText = view.findViewById(R.id.loginInputBrith)
        val tbirth = birth.text
        val profile : EditText = view.findViewById(R.id.loginInputProfileMsg)
        val tProfile = profile.text
        val btn : Button = view.findViewById(R.id.loginInputBtn)
        val map = LinkedHashMap<String, Any>()
        val map2 = LinkedHashMap<String,String?>()

        viewModel = ViewModelProvider(requireActivity(), LoginViewModelFactory(LoginRepository())).get(LoginViewModel::class.java)

        CoroutineScope(Dispatchers.Main).launch {
            Log.d(TAG, "onCreateView: ${viewModel.loginModel2.size}")
            Log.d(TAG, "onCreateView: ${viewModel.loginModel.size}")
            getLastInfo(viewModel)
            registerToken = viewModel.loginModel.get(0).data.register_token.toString()
            code = viewModel.loginModel2.get(0).inviteCode.toString()
            userName = viewModel.loginModel2.get(0).data?.name.toString()
            userGrade = viewModel.loginModel2.get(0).data?.grade.toString()
            userSession = viewModel.loginModel2.get(0).data?.session.toString()
            name.text = userName
            grade.text = userGrade
            session.text = userSession
            map.put("register_token",registerToken)
            map.put("code",code)
            map2.put("birth",tbirth.toString())
            map2.put("phone",tPhone.toString())
            map2.put("major",tMajor.toString())
            map2.put("profile_message",tPhone.toString())
            map.put("data", map2)

        }

        btn.setOnClickListener {
            lifecycleScope.launch {
                postMoreInfo(map)
                viewModel.loginRepositories3.observe(requireActivity()){
                    Log.d(TAG, "onCreateView: $it")

                }
            }
        }

        return view
    }

    private fun postMoreInfo(userInfo : HashMap<String, Any>){
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