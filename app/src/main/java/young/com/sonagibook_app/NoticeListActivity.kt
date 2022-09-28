package young.com.sonagibook_app

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
    private val tokenDB by lazy { TokenDatabase.getInstance(this) }
    lateinit var binding : ActivityNoticeListBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var mainViewModelFactory: MainViewModelFactory
    val noticeList = ArrayList<RetrofitResponseNoticeDto>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoticeListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModelFactory = MainViewModelFactory(Repository())
        viewModel = ViewModelProvider(this, mainViewModelFactory).get(MainViewModel::class.java)

        CoroutineScope(Dispatchers.Main).launch {
            val token =
                withContext(CoroutineScope(Dispatchers.IO).coroutineContext) { tokenDB?.tokenDao()?.getAll() }
            val accessToken = "Bearer ${token?.accessToken}"
            getNoticeList(1,accessToken)
            viewModel.repositories2.observe(this@NoticeListActivity){
                noticeList.add(it)
                Log.d(TAG, "onCreate: 성공")
            }
            val adapter = NoticeListItemsAdapter(viewModel.homeNoticeDataModel)
            binding.noticeListNoticeContainer.layoutManager =
                LinearLayoutManager(this@NoticeListActivity, LinearLayoutManager.VERTICAL,false)
            binding.noticeListNoticeContainer.adapter = adapter
        }

        binding.noticeListAddBtn.setOnClickListener {
            val intent = Intent(this,NoticeAddActivity::class.java)
            startActivity(intent)
        }
    }

    private suspend fun getNoticeList(page : Int, token : String){
        viewModel.getNoticeList(page, token)
    }
    private fun fetchToken(){

    }


}