package young.com.sonagibook_app

import android.content.ContentValues.TAG
import android.content.Intent
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

        u.setOnClickListener {
            val intent = Intent(context, BookAddActivity::class.java)
            startActivity(intent)
        }

        CoroutineScope(Dispatchers.Main).launch {
            fetchBook()
            Log.d(TAG, "결과: ${viewModel.bookDataModel}")
        }

        return view
    }

    private suspend fun fetchBook(){
        Log.d(TAG, "fetchBook: 시작")
        withContext(Dispatchers.IO){
            while (viewModel.bookDataModel.size==0){}
        }
        Log.d(TAG, "fetchBook: 끝")
    }

}