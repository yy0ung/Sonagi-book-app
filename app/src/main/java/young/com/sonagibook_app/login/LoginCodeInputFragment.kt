package young.com.sonagibook_app.login

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import young.com.sonagibook_app.R
import young.com.sonagibook_app.retrofit.Dto.RetrofitUserInfoGetDto
import young.com.sonagibook_app.retrofit.LoginRepository

class LoginCodeInputFragment : Fragment() {
    private lateinit var viewModel : LoginViewModel
    private lateinit var viewModelFactory: LoginViewModelFactory
    var imm : InputMethodManager? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login_code_input, container, false)
        val code = view.findViewById<TextView>(R.id.code)
        val input = view.findViewById<EditText>(R.id.codeInput)
        val inputCode = input.text
        val btn : Button = view.findViewById(R.id.loginCodeProofBtn)

        viewModel = ViewModelProvider(requireActivity(), LoginViewModelFactory(LoginRepository())).get(
            LoginViewModel::class.java)

        imm = context?.getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as InputMethodManager?


        btn.setOnClickListener {
            lifecycleScope.launch {
                getToken(inputCode.toString(), viewModel.loginModel.get(0).data.register_token.toString())
                viewModel.loginRepositories2.observe(requireActivity()){
                    Log.d(TAG, "onCreateView: $it")
                    val userInfo = RetrofitUserInfoGetDto(it.success, it.msg, it.data, inputCode.toString(),null)
                    (activity as LoginActivity).click2(userInfo)
                }
            }
        }

        return view
    }

    private suspend fun getToken(code : String, token : String){
        viewModelFactory = LoginViewModelFactory(LoginRepository())
        viewModel = ViewModelProvider(this,viewModelFactory).get(LoginViewModel::class.java)

        viewModel.getToken(code, token)

    }
}