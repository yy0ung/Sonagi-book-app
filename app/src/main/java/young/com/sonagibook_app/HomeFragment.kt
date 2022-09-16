package young.com.sonagibook_app

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import young.com.sonagibook_app.login.LoginViewModel
import young.com.sonagibook_app.login.LoginViewModelFactory
import young.com.sonagibook_app.retrofit.LoginRepository

class HomeFragment : Fragment() {
    private lateinit var mainViewModelFactory: MainViewModelFactory
    private lateinit var viewModel: MainViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container,false)
        val info = view.findViewById<TextView>(R.id.homeProfileInfo)
        val profileMsg = view.findViewById<TextView>(R.id.homeProfileMsg)

        val btn = view.findViewById<LinearLayout>(R.id.homeUpcomingContainer)
        viewModel = ViewModelProvider(requireActivity(), MainViewModelFactory(Repository())).get(
            MainViewModel::class.java)

        lifecycleScope.launch {
            fetchUserInfo()
            val token = "Bearer ${viewModel.accessToken.get(0)}"
            getAccessToken(token)

            //main 에서 받아야함
        }




        return view
    }

    private suspend fun getAccessToken(token : String) {
        mainViewModelFactory = MainViewModelFactory(Repository())
        viewModel = ViewModelProvider(this, mainViewModelFactory).get(MainViewModel::class.java)
        viewModel.getAccessToken(token)

    }
    private suspend fun fetchUserInfo(){
        withContext(Dispatchers.IO){
            while (viewModel.accessToken.size==0){}
        }
    }
}