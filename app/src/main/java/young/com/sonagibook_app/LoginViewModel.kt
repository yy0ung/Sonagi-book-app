package young.com.sonagibook_app

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import young.com.sonagibook_app.retrofit.Dto.RetrofitPostRequestDto
import young.com.sonagibook_app.retrofit.Dto.RetrofitPostResponseDto
import young.com.sonagibook_app.retrofit.LoginRepository

class LoginViewModel(loginRepository: LoginRepository) : ViewModel() {
    private val _loginRepository = MutableLiveData<RetrofitPostResponseDto>()
    val loginRepository = _loginRepository

    fun postToken(token : RetrofitPostRequestDto){
        viewModelScope.launch {

        }
    }
}