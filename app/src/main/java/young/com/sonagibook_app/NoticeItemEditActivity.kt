package young.com.sonagibook_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import young.com.sonagibook_app.databinding.ActivityNoticeItemEditBinding
import young.com.sonagibook_app.room.Token
import young.com.sonagibook_app.room.TokenDatabase

class NoticeItemEditActivity : AppCompatActivity() {
    private val tokenDB by lazy { TokenDatabase.getInstance(this) }
    lateinit var binding: ActivityNoticeItemEditBinding
    lateinit var viewModel : NoticeEditViewModel
    lateinit var viewModelFactory : NoticeEditViewModelFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoticeItemEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModelFactory = NoticeEditViewModelFactory(Repository())
        viewModel = ViewModelProvider(this, viewModelFactory)[NoticeEditViewModel::class.java]

        CoroutineScope(Dispatchers.Main).launch {
            val nid = intent.getStringExtra("nid")
            val token =
                withContext(CoroutineScope(Dispatchers.IO).coroutineContext) { getTokenDB() }
            val accessToken = "Bearer ${token?.accessToken}"
            getNoticeContent(nid.toString(), accessToken)
            viewModel.repositories1.observe(this@NoticeItemEditActivity){
                binding.noticeEditInputTitle.setText(it.data.title.toString())
                binding.noticeEditInputContext.setText(it.data.content.toString())

            }
        }

    }

    private suspend fun getNoticeContent(nid : String, token : String){
        viewModel.getNoticeContent(nid, token)
    }

    private suspend fun getTokenDB() : Token?{
        return tokenDB?.tokenDao()?.getAll()
    }
}