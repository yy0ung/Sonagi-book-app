package young.com.sonagibook_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import young.com.sonagibook_app.login.LoginActivity
import young.com.sonagibook_app.login.LoginViewModel
import young.com.sonagibook_app.login.LoginViewModelFactory
import young.com.sonagibook_app.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    var token : String = ""
    private lateinit var viewModel: MainViewModel
    private lateinit var viewModelFactory: MainViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this,MainViewModelFactory(Repository()))
            .get(MainViewModel::class.java)

        val accessToken : String = intent.getStringExtra("accessToken")!!
        viewModel.accessToken.add(accessToken)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragment = supportFragmentManager.beginTransaction()
        fragment.add(R.id.homeMainFragment, HomeFragment()).commit()




    }



}