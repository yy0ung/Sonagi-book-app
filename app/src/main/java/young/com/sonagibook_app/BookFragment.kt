package young.com.sonagibook_app

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import young.com.sonagibook_app.retrofit.Dto.BookDto
import young.com.sonagibook_app.retrofit.Dto.RetrofitPostBookDto
import young.com.sonagibook_app.room.TokenDatabase
import kotlin.math.log

class BookFragment : Fragment() {
    private val tokenDB by lazy { context?.let { TokenDatabase.getInstance(it) } }
    private lateinit var viewModel: MainViewModel
    private lateinit var viewModelFactory: MainViewModelFactory
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_book, container, false)
        val u : TextView = view.findViewById(R.id.bookUpload)
        val g : TextView = view.findViewById(R.id.bookGet)
        viewModel = ViewModelProvider(requireActivity(), MainViewModelFactory(Repository()))[MainViewModel::class.java]

        val data = RetrofitPostBookDto(BookDto("예약 테스트", 0, "202211200900", "202211201000" ))

        CoroutineScope(Dispatchers.Main).launch {
            val token =
                withContext(CoroutineScope(Dispatchers.IO).coroutineContext) { tokenDB?.tokenDao()?.getAll() }
            val accessToken = "Bearer ${token?.accessToken}"
            Log.d(TAG, "onCreateView: 토큰 $accessToken")
            u.setOnClickListener { viewModel.postBook(accessToken, data) }



        }

        return view
    }

}