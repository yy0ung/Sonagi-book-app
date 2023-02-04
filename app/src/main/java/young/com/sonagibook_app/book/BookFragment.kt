package young.com.sonagibook_app.book

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
import young.com.sonagibook_app.MainViewModel
import young.com.sonagibook_app.MainViewModelFactory
import young.com.sonagibook_app.R
import young.com.sonagibook_app.Repository
import young.com.sonagibook_app.databinding.FragmentBookBinding
import young.com.sonagibook_app.retrofit.Dto.RetrofitResponseBookDto
import young.com.sonagibook_app.room.TokenDatabase

class BookFragment : Fragment() {
    private val tokenDB by lazy { context?.let { TokenDatabase.getInstance(it) } }
    private lateinit var viewModel: MainViewModel
    private lateinit var viewModelFactory: MainViewModelFactory
    private val temp = ArrayList<RetrofitResponseBookDto>()
    private var _binding : FragmentBookBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookBinding.inflate(layoutInflater)
        val view = binding.root
        viewModel = ViewModelProvider(requireActivity(), MainViewModelFactory(Repository()))[MainViewModel::class.java]


        CoroutineScope(Dispatchers.Main).launch {
            fetchBook()
            Log.d(TAG, "결과: ${viewModel.bookDataModel1}")
            val adapter = BookListAdapter(viewModel.bookDataModel1, requireActivity().supportFragmentManager)

            binding.bookCalendarList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            binding.bookCalendarList.adapter = adapter
        }

        binding.add.setOnClickListener {
            val bottomSheet = BookAddDialog()
            bottomSheet.show(requireActivity().supportFragmentManager,bottomSheet.tag)
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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