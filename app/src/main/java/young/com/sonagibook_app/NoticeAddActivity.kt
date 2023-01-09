package young.com.sonagibook_app

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
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
    private val tokenDB by lazy { TokenDatabase.getInstance(applicationContext) }
    lateinit var binding : ActivityNoticeAddBinding
    private lateinit var viewModel : NoticeViewModel
    private lateinit var viewModelFactory : NoticeViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoticeAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModelFactory = NoticeViewModelFactory((Repository()))
        viewModel = ViewModelProvider(this, viewModelFactory).get(NoticeViewModel::class.java)

        binding.noticeAddSendBtn.setOnClickListener {
            addNoticeItem()
        }
        binding.noticeAddCancelBtn.setOnClickListener {
            finish()
        }

    }

    private suspend fun postNewNotice(token : String, noticeInfo : RetrofitPostNoticeDto){
        viewModel.postNewNotice(token, noticeInfo)
    }

    private fun addNoticeItem(){
        var title = findViewById<EditText>(R.id.noticeAddInputTitle)
        var content = findViewById<EditText>(R.id.noticeAddInputContext)

        val notice = RetrofitPostNoticeDto(NoticeDto(title.text.toString(),content.text.toString(), true))
        Toast.makeText(this, "${notice},,${title.text.toString()}",Toast.LENGTH_LONG).show()

        CoroutineScope(Dispatchers.Main).launch {
            val token =
                withContext(CoroutineScope(Dispatchers.IO).coroutineContext) { tokenDB?.tokenDao()?.getAll() }
            val accessToken = "Bearer ${token?.accessToken}"
            postNewNotice(accessToken, notice)
            Log.d(ContentValues.TAG, "Activity onCreate: 공지 올리기 성공")

        }
    }
}