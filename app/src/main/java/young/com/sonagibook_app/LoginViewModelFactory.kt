package young.com.sonagibook_app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import young.com.sonagibook_app.retrofit.LoginRepository


class LoginViewModelFactory(private val loginRepository: LoginRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(LoginRepository::class.java).newInstance(loginRepository)

    }
}