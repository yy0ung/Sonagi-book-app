package young.com.sonagibook_app

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import young.com.sonagibook_app.retrofit.Dto.RetrofitPostRequestDto
import young.com.sonagibook_app.retrofit.Dto.RetrofitPostResponseDto
import young.com.sonagibook_app.retrofit.LoginRepository

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {
    private val _loginRepositories = MutableLiveData<RetrofitPostResponseDto>()
    val loginRepositories = _loginRepositories
    init {
        Log.d(TAG, "create: viewModel")
    }

    fun postToken(token : RetrofitPostRequestDto){
        Log.d(TAG, "postToken: postpost")
        viewModelScope.launch {
            loginRepository.postToken(token).let { response ->
                if(response.isSuccessful){
                    Log.d(TAG, "postToken: ${response.body()}")
                    _loginRepositories.postValue(response.body())
                }
            }
        }
    }
}