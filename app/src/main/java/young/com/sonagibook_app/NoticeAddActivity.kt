package young.com.sonagibook_app

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import young.com.sonagibook_app.databinding.ActivityNoticeAddBinding
import young.com.sonagibook_app.retrofit.Dto.NoticeDto
import young.com.sonagibook_app.retrofit.Dto.RetrofitPostNoticeDto
import young.com.sonagibook_app.room.TokenDatabase

class NoticeAddActivity : AppCompatActivity() {
    private val tokenDB by lazy { TokenDatabase.getInstance(this) }
    lateinit var binding : ActivityNoticeAddBinding
    private lateinit var viewModel : NoticeViewModel
    private lateinit var viewModelFactory : NoticeViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoticeAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var noticeTitle  = binding.noticeAddInputTitle
        val title = noticeTitle.text
        val noticeContent = binding.noticeAddInputContext.text.toString()
        //val accessToken : String = intent.getStringExtra("accessToken")!!
        viewModelFactory = NoticeViewModelFactory((Repository()))
        viewModel = ViewModelProvider(this, viewModelFactory).get(NoticeViewModel::class.java)

        val notice = RetrofitPostNoticeDto(NoticeDto("0929 test2","0929 text content2", true))


        //val token = "Bearer $accessToken"
        //Toast.makeText(this,token, Toast.LENGTH_LONG).show()

        binding.noticeAddSendBtn.setOnClickListener {
            //Toast.makeText(this,"${a.title},,${title.toString()}",Toast.LENGTH_LONG).show()
            CoroutineScope(Dispatchers.Main).launch {
                val token =
                    withContext(CoroutineScope(Dispatchers.IO).coroutineContext) { tokenDB?.tokenDao()?.getAll() }
                val accessToken = "Bearer ${token?.accessToken}"
                postNewNotice(accessToken, notice)
                Log.d(ContentValues.TAG, "Activity onCreate: success")

            }
        }

    }

    private suspend fun postNewNotice(token : String, noticeInfo : RetrofitPostNoticeDto){
        viewModel.postNewNotice(token, noticeInfo)
    }
}