package young.com.sonagibook_app

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.TableLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import young.com.sonagibook_app.retrofit.Dto.BookDto
import young.com.sonagibook_app.retrofit.Dto.RetrofitPostBookDto
import young.com.sonagibook_app.retrofit.Dto.RetrofitResponseBookDto
import young.com.sonagibook_app.retrofit.dataDto.dataDtoNestedUser
import young.com.sonagibook_app.room.TokenDatabase
import kotlin.math.log

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
        val h = view.findViewById<TableLayout>(R.id.bookCalendarHeader)
        viewModel = ViewModelProvider(requireActivity(), MainViewModelFactory(Repository()))[MainViewModel::class.java]
        h.setOnClickListener {
            val intent = Intent(context, BookAddActivity::class.java)
            startActivity(intent)
        }

        CoroutineScope(Dispatchers.Main).launch {
            fetchBook()
            Log.d(TAG, "결과: ${viewModel.bookDataModel}")
            val adapter = BookListAdapter(viewModel.bookDataModel)

            calendarRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            calendarRecyclerView.adapter = adapter
        }

        return view
    }

    private suspend fun fetchBook(){
        Log.d(TAG, "fetchBook: 시작")
        withContext(Dispatchers.IO){
            while (viewModel.bookDataModel.size==0){}
        }
        //temp.add(RetrofitResponseBookDto(dataDtoNestedUser(null, null, null),
        //    "title", 0, "2022-10-25 09:00:00","2022-10-25 10:00:00", 1, "2022-10-10 09:00:00","2022-10-10 09:00:00"))

        Log.d(TAG, "fetchBook: 끝")
    }

}