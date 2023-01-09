package young.com.sonagibook_app

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import young.com.sonagibook_app.databinding.ActivityNoticeListBinding
import young.com.sonagibook_app.retrofit.Dto.RetrofitResponseNoticeDto
import young.com.sonagibook_app.room.TokenDatabase

class NoticeListActivity : AppCompatActivity() {
    private val tokenDB by lazy { TokenDatabase.getInstance(applicationContext) }
    lateinit var binding : ActivityNoticeListBinding
    private lateinit var viewModel: NoticeListViewModel
    private lateinit var noticeListViewModelFactory: NoticeListViewModelFactory
    private val noticeList = ArrayList<RetrofitResponseNoticeDto>()
    private val searchList = ArrayList<RetrofitResponseNoticeDto>()


    override fun onResume() {
        super.onResume()
        binding = ActivityNoticeListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d(TAG, "onResume: 리로드 로그")
        CoroutineScope(Dispatchers.Main).launch {
            val token =
                withContext(CoroutineScope(Dispatchers.IO).coroutineContext) { tokenDB?.tokenDao()?.getAll() }
            val accessToken = "Bearer ${token?.accessToken}"
            getNoticeList(1,accessToken)
            viewModel.repositories1.observe(this@NoticeListActivity){
                noticeList.add(it)
                //Log.d(TAG, "onCreate: 성공")
                val adapter = NoticeListItemsAdapter(noticeList)
                binding.noticeListNoticeContainer.layoutManager =
                    LinearLayoutManager(this@NoticeListActivity, LinearLayoutManager.VERTICAL,false)
                binding.noticeListNoticeContainer.adapter = adapter
            }

        }

        //toolbar
        setSupportActionBar(binding.noticeListToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        binding.noticeListToolbar.setNavigationOnClickListener { onBackPressed() }

        noticeListViewModelFactory = NoticeListViewModelFactory(Repository())
        viewModel = ViewModelProvider(this,noticeListViewModelFactory)[NoticeListViewModel::class.java]



        binding.noticeListAddBtn.setOnClickListener {
            val intent = Intent(this,NoticeAddActivity::class.java)
            startActivity(intent)
        }

        var searchView : SearchView.OnQueryTextListener =
            object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(s: String): Boolean {
                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                NoticeListItemsAdapter(noticeList).filter.filter(s)
                return false
            }
        }

        binding.noticeListToolbarSearchInput.setOnQueryTextListener(searchView)
    }

    private suspend fun getNoticeList(page : Int, token : String){
        viewModel.getNoticeList(page, token)
    }



}