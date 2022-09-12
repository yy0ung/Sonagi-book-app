package young.com.sonagibook_app

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import young.com.sonagibook_app.retrofit.Dto.RetrofitPostRequestDto
import young.com.sonagibook_app.retrofit.Dto.RetrofitPostResponseDto
import young.com.sonagibook_app.retrofit.Dto.RetrofitUserInfoGetDto
import young.com.sonagibook_app.retrofit.LoginRepository

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {
    private val _loginRepositoriesPostToken = MutableLiveData<RetrofitPostResponseDto>()
    val loginRepositories1 : MutableLiveData<RetrofitPostResponseDto>
        get() = _loginRepositoriesPostToken

    private val _loginRepositoriesGetToken = MutableLiveData<RetrofitUserInfoGetDto>()
    val loginRepositories2 : MutableLiveData<RetrofitUserInfoGetDto>
        get() = _loginRepositoriesGetToken

    private val _loginRepositoriesPostInfo = MutableLiveData<RetrofitPostResponseDto>()
    val loginRepositories3 : MutableLiveData<RetrofitPostResponseDto>
        get() = _loginRepositoriesPostInfo

    // 합쳐줘야 함
    public val loginModel = ArrayList<RetrofitPostResponseDto>()
    public val loginModel2 = ArrayList<RetrofitUserInfoGetDto>()

    init {
        Log.d(TAG, "create: viewModel")
    }

    fun postToken(token : RetrofitPostRequestDto){
        Log.d(TAG, "postToken: postpost")
        viewModelScope.launch {
            loginRepository.postToken(token).let { response ->
                if(response.isSuccessful){
                    Log.d(TAG, "postToken: ${response.body()}")
                    _loginRepositoriesPostToken.postValue(response.body())
//                    response.body()?.let { loginModel.add(it) }
                }
            }
        }
    }

    fun getToken(code : String, token : String){
        Log.d(TAG, "getToken: getget")
        viewModelScope.launch {
            loginRepository.getToken(code, token).let { response ->
                if(response.isSuccessful){
                    Log.d(TAG, "getToken: ${response.body()}")
                    _loginRepositoriesGetToken.postValue(response.body())

                }
            }
        }
    }

    fun postMoreInfo(userInfo : HashMap<String, Any>){
        Log.d(TAG, "postMoreInfo: postpostMore")
        viewModelScope.launch {
            loginRepository.postMoreInfo(userInfo).let { response ->
                if(response.isSuccessful){
                    Log.d(TAG, "postMoreInfo: ${response.body()}")
                    _loginRepositoriesPostInfo.postValue(response.body())
                }
            }
        }
    }
}