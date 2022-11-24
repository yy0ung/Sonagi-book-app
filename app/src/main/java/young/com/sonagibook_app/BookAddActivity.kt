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
import young.com.sonagibook_app.databinding.ActivityBookAddBinding
import young.com.sonagibook_app.retrofit.Dto.BookDto
import young.com.sonagibook_app.retrofit.Dto.RetrofitPostBookDto
import young.com.sonagibook_app.room.TokenDatabase

class BookAddActivity : AppCompatActivity() {
    private val tokenDB by lazy { TokenDatabase.getInstance(this) }
    private lateinit var viewModel: BookAddViewModel
    private lateinit var viewModelFactory: BookAddViewModelFactory
    private lateinit var binding : ActivityBookAddBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBookAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModelFactory = BookAddViewModelFactory(Repository())
        viewModel = ViewModelProvider(this,viewModelFactory)[BookAddViewModel::class.java]

        val data = RetrofitPostBookDto(BookDto("예약 테스트", 0, "202211240900", "202211241000" ))

        CoroutineScope(Dispatchers.Main).launch {
            val token =
                withContext(CoroutineScope(Dispatchers.IO).coroutineContext) { tokenDB?.tokenDao()?.getAll() }
            val accessToken = "Bearer ${token?.accessToken}"
            binding.bookSendBtn.setOnClickListener { viewModel.postBook(accessToken, data) }



        }

    }
}