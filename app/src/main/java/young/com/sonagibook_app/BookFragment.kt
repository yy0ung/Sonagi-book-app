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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import young.com.sonagibook_app.retrofit.Dto.RetrofitResponseBookDto
import young.com.sonagibook_app.room.TokenDatabase

class BookFragment : Fragment() {
    private val tokenDB by lazy { context?.let { TokenDatabase.getInstance(it) } }
    private lateinit var viewModel: MainViewModel
    private lateinit var viewModelFactory: MainViewModelFactory
    private val temp = ArrayList<RetrofitResponseBookDto>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_book, container, false)
        val calendarRecyclerView = view.findViewById<RecyclerView>(R.id.bookCalendarList)
        viewModel = ViewModelProvider(requireActivity(), MainViewModelFactory(Repository()))[MainViewModel::class.java]


        CoroutineScope(Dispatchers.Main).launch {
            fetchBook()
            Log.d(TAG, "결과: ${viewModel.bookDataModel1}")
            val adapter = BookListAdapter(viewModel.bookDataModel1, requireActivity().supportFragmentManager)

            calendarRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            calendarRecyclerView.adapter = adapter
        }

        view.findViewById<TextView>(R.id.add).setOnClickListener {
            val bottomSheet = BookAddDialog()
            bottomSheet.show(requireActivity().supportFragmentManager,bottomSheet.tag)
        }

        return view
    }

    private suspend fun fetchBook(){
        Log.d(TAG, "fetchBook: 시작")
        withContext(Dispatchers.IO){
            while (viewModel.bookDataModel1.size==0 || viewModel.bookDataModel2.size==0){}
        }
        //temp.add(RetrofitResponseBookDto(dataDtoNestedUser(null, null, null),
        //    "title", 0, "2022-10-25 09:00:00","2022-10-25 10:00:00", 1, "2022-10-10 09:00:00","2022-10-10 09:00:00"))

        Log.d(TAG, "fetchBook: 끝")
    }

}