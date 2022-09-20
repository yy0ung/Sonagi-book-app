package young.com.sonagibook_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import young.com.sonagibook_app.databinding.ActivityNoticeListBinding
import young.com.sonagibook_app.retrofit.Dto.RetrofitResponseNoticeDto

class NoticeListActivity : AppCompatActivity() {
    lateinit var binding : ActivityNoticeListBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var mainViewModelFactory: MainViewModelFactory
    val noticeList = ArrayList<RetrofitResponseNoticeDto>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoticeListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val token : String = intent.getStringExtra("accessToken")!!
        mainViewModelFactory = MainViewModelFactory(Repository())
        viewModel = ViewModelProvider(this, mainViewModelFactory).get(MainViewModel::class.java)

        CoroutineScope(Dispatchers.Main).launch {
            getNoticeList(1,token)
            viewModel.repositories2.observe(this@NoticeListActivity){
                noticeList.add(it)
            }
            val adapter = NoticeListItemsAdapter(viewModel.homeNoticeDataModel)
            binding.noticeListNoticeContainer.layoutManager =
                LinearLayoutManager(this@NoticeListActivity, LinearLayoutManager.VERTICAL,false)
            binding.noticeListNoticeContainer.adapter = adapter
        }

        binding.noticeListAddBtn.setOnClickListener {
            val intent = Intent(this,NoticeAddActivity::class.java)
            Toast.makeText(this,token,Toast.LENGTH_LONG).show()
            intent.putExtra("accessToken",token)
            startActivity(intent)
        }
    }

    private suspend fun getNoticeList(page : Int, token : String){
        viewModel.getNoticeList(page, token)
    }


}