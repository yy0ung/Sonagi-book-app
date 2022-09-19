package young.com.sonagibook_app

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import young.com.sonagibook_app.databinding.ActivityNoticeAddBinding
import young.com.sonagibook_app.retrofit.Dto.RetrofitPostNoticeDto

class NoticeAddActivity : AppCompatActivity() {
    lateinit var binding : ActivityNoticeAddBinding
    private lateinit var viewModel : NoticeViewModel
    private lateinit var viewModelFactory : NoticeViewModelFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoticeAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val noticeTitle : String = binding.noticeAddInputTitle.text.toString()
        val noticeContext : String = binding.noticeAddInputContext.text.toString()
        val accessToken : String = intent.getStringExtra("accessToken")!!
        viewModelFactory = NoticeViewModelFactory((Repository()))
        viewModel = ViewModelProvider(this, viewModelFactory).get(NoticeViewModel::class.java)

        val notice = RetrofitPostNoticeDto("test용 notice", "0920 text notice", 0)
        val token = "Bearer $accessToken"
        Toast.makeText(this,token, Toast.LENGTH_LONG).show()

        binding.noticeAddSendBtn.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                postNewNotice(token, notice)
                Log.d(ContentValues.TAG, "Activity onCreate: success")

            }
        }

    }

    private suspend fun postNewNotice(token : String, noticeInfo : RetrofitPostNoticeDto){
        viewModel.postNewNotice(token, noticeInfo)
    }
}