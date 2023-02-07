package young.com.sonagibook_app.home

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import young.com.sonagibook_app.*
import young.com.sonagibook_app.databinding.FragmentHomeBinding
import young.com.sonagibook_app.notice.NoticeItemsAdapter
import young.com.sonagibook_app.notice.NoticeListActivity
import young.com.sonagibook_app.retrofit.Dto.RetrofitResponseNoticeDto
import young.com.sonagibook_app.room.Token
import young.com.sonagibook_app.room.TokenDatabase

class HomeFragment : Fragment() {
    private val tokenDB by lazy { TokenDatabase.getInstance(requireContext().applicationContext) }
    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var mainViewModelFactory: MainViewModelFactory
    private lateinit var viewModel: MainViewModel
    var adapter : NoticeItemsAdapter? = null
    var data = ArrayList<RetrofitResponseNoticeDto>()

    @SuppressLint("SetTextI18n")

    override fun onStart() {
        super.onStart()
        CoroutineScope(Dispatchers.Main).launch {
            val accessToken = "Bearer ${withContext(CoroutineScope(Dispatchers.IO).coroutineContext) { getTokenDB() }?.accessToken}"
            getNoticeList(1,accessToken)
            viewModel.repositories2.observe(viewLifecycleOwner){
                Log.d(TAG, "onCreateView: fragment fffffff $it")
                val adapter = NoticeItemsAdapter(it)
                binding.homeNoticeContainer.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
                binding.homeNoticeContainer.adapter = adapter
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        val view = binding.root

        CoroutineScope(Dispatchers.Main).launch {
            val accessToken = "Bearer ${withContext(CoroutineScope(Dispatchers.IO).coroutineContext) { getTokenDB() }?.accessToken}"
            getNoticeList(1,accessToken)
            viewModel.repositories2.observe(viewLifecycleOwner){
                Log.d(TAG, "onCreateView: fragment fffffff $it")
                val adapter = NoticeItemsAdapter(it)
                binding.homeNoticeContainer.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
                binding.homeNoticeContainer.adapter = adapter
            }
        }


        val btn = view.findViewById<LinearLayout>(R.id.homeUpcomingContainer)
        viewModel = ViewModelProvider(requireActivity(), MainViewModelFactory(Repository())).get(
            MainViewModel::class.java)

        CoroutineScope(Dispatchers.Main).launch {
            fetchUserInfo()
            val profile = viewModel.userHomeDataModel.get(0).data
            binding.homeProfileInfo.text = "${profile.grade}${profile.session} ${profile.name}님"
            //profileMsg.text = profile.profile_message.toString()
        }

        binding.homeNoticeMoreBtn.setOnClickListener {
            val intent = Intent(context, NoticeListActivity::class.java)
            //intent.putExtra("accessToken",viewModel.accessToken[0].toString())
            startActivity(intent)
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private suspend fun getTokenDB() : Token?{
        return tokenDB?.tokenDao()?.getAll()
    }

    private suspend fun getNoticeList(page : Int, token : String){
        viewModel.getNoticeList(page, token)
    }

    private suspend fun fetchUserInfo(){
        withContext(Dispatchers.IO){
            while (viewModel.userHomeDataModel.size==0){}
        }
    }

    private suspend fun fetchNoticeInfo(){

        withContext(Dispatchers.IO){
            while (viewModel.homeNoticeDataModel.size==0){}
        }
        Log.d(TAG, "fetchNoticeInfo: fetch done")
    }
    //@SuppressLint("NotifyDataSetChanged")
//    fun refreshAdapter(retrofitResponseNoticeDto: ArrayList<RetrofitResponseNoticeDto>) {
//        val noticeRecycler = view?.findViewById<RecyclerView>(R.id.homeNoticeContainer)
//        data = retrofitResponseNoticeDto
//        adapter = NoticeItemsAdapter(data)
//        noticeRecycler?.layoutManager =LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
//        noticeRecycler?.adapter = adapter
//        adapter?.notifyDataSetChanged()
//
//    }

    
}