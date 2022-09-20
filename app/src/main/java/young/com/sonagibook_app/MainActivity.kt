package young.com.sonagibook_app

import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import young.com.sonagibook_app.login.LoginActivity
import young.com.sonagibook_app.login.LoginViewModel
import young.com.sonagibook_app.login.LoginViewModelFactory
import young.com.sonagibook_app.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    var token : String = ""
    private lateinit var viewModel: MainViewModel
    private lateinit var mainViewModelFactory: MainViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        

        mainViewModelFactory = MainViewModelFactory(Repository())
        viewModel = ViewModelProvider(this, mainViewModelFactory).get(MainViewModel::class.java)

        val accessToken : String = intent.getStringExtra("accessToken")!!
        val token = "Bearer $accessToken"
        CoroutineScope(Dispatchers.Main).launch {
            getAccessToken(token)
            fetchUserInfo()
            Log.d(TAG, "Activity onCreate: ${viewModel.userHomeDataModel.get(0).data.birth}")

            //main 에서 받아야함
        }

        CoroutineScope(Dispatchers.Main).launch { 
            getNoticeList(1,token)
            viewModel.repositories2.observe(this@MainActivity){
                Log.d(TAG, "onCreate: $it")
                viewModel.homeNoticeDataModel.add(it)
            }
        }

        viewModel.accessToken.add(accessToken)

        val fragment = supportFragmentManager.beginTransaction()
        fragment.add(R.id.homeMainFragment, HomeFragment()).commit()
        changeColorToBlue(binding.homeNav1)

        binding.homeNav1.setOnClickListener{
            changeColorToBlue(binding.homeNav1)
            changeColorToGray(binding.homeNav5, binding.homeNav4, binding.homeNav3,binding.homeNav2)
            supportFragmentManager.beginTransaction().replace(R.id.homeMainFragment, HomeFragment()).commit()
        }
        binding.homeNav2.setOnClickListener{
            changeColorToBlue(binding.homeNav2)
            changeColorToGray(binding.homeNav5, binding.homeNav4, binding.homeNav3,binding.homeNav1)
            supportFragmentManager.beginTransaction().replace(R.id.homeMainFragment, ScheduleFragment()).commit()
        }
        binding.homeNav3.setOnClickListener{
            changeColorToBlue(binding.homeNav3)
            changeColorToGray(binding.homeNav5, binding.homeNav4, binding.homeNav2,binding.homeNav1)
            supportFragmentManager.beginTransaction().replace(R.id.homeMainFragment, BookFragment()).commit()
        }
        binding.homeNav4.setOnClickListener{
            changeColorToBlue(binding.homeNav4)
            changeColorToGray(binding.homeNav5, binding.homeNav2, binding.homeNav3,binding.homeNav1)
            supportFragmentManager.beginTransaction().replace(R.id.homeMainFragment, ContactFragment()).commit()
        }
        binding.homeNav5.setOnClickListener{
            changeColorToBlue(binding.homeNav5)
            changeColorToGray(binding.homeNav2, binding.homeNav4, binding.homeNav3,binding.homeNav1)
            supportFragmentManager.beginTransaction().replace(R.id.homeMainFragment, MoreFragment()).commit()
        }






    }

    private suspend fun getAccessToken(token : String) {
        viewModel.getAccessToken(token)

    }
    private suspend fun getNoticeList(page : Int, token : String){
        viewModel.getNoticeList(page, token)
    }
    private suspend fun fetchUserInfo(){
        withContext(Dispatchers.IO){
            while (viewModel.userHomeDataModel.size==0){}
        }
    }

    private fun changeColorToBlue(imgG : ImageView){
        imgG.setColorFilter(Color.parseColor("#6773F6"))
    }

    private fun changeColorToGray(g1:ImageView, g2:ImageView, g3:ImageView, g4:ImageView){
        g1.setColorFilter(Color.parseColor("#C8CBD0"))
        g2.setColorFilter(Color.parseColor("#C8CBD0"))
        g3.setColorFilter(Color.parseColor("#C8CBD0"))
        g4.setColorFilter(Color.parseColor("#C8CBD0"))

    }



}