package young.com.sonagibook_app

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import young.com.sonagibook_app.retrofit.LoginRepository

class HomeFragment : Fragment() {
    private lateinit var viewModel: LoginViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container,false)
        Log.d(TAG, "onCreateView: abc")
        viewModel = ViewModelProvider(requireActivity(),LoginViewModelFactory(LoginRepository())).get(LoginViewModel::class.java)

//        Log.d(TAG, "onCreateView: ${viewModel.getModel().value?.success}")
        

        return view
    }
}