package young.com.sonagibook_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import young.com.sonagibook_app.databinding.ActivityScheduleItemEditBinding
import young.com.sonagibook_app.retrofit.Dto.RetrofitPostScheduleDto
import young.com.sonagibook_app.retrofit.Dto.ScheduleDto
import young.com.sonagibook_app.room.Token
import young.com.sonagibook_app.room.TokenDatabase

class ScheduleItemEditActivity : AppCompatActivity() {
    private val tokenDB by lazy { TokenDatabase.getInstance(this) }
    lateinit var binding : ActivityScheduleItemEditBinding
    lateinit var viewModel : ScheduleItemEditViewModel
    lateinit var viewModelFactory: ScheduleEditViewModelFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScheduleItemEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModelFactory = ScheduleEditViewModelFactory(Repository())
        viewModel = ViewModelProvider(this, viewModelFactory)[ScheduleItemEditViewModel::class.java]

        CoroutineScope(Dispatchers.Main).launch {
            val eid = intent.getStringExtra("eid")
            val token = withContext(CoroutineScope(Dispatchers.IO).coroutineContext){ getTokenDB() }
            val accessToken = "Bearer ${token?.accessToken}"
            getScheduleContent(eid.toString(), accessToken)
            viewModel.repositories1.observe(this@ScheduleItemEditActivity){
                binding.scheduleEditInputTitle.setText(it.data.title.toString())
                binding.scheduleEditInputContext.setText(it.data.content.toString())
            }

            binding.scheduleAddSendBtn.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch { editContent(eid.toString(), accessToken.toString()) }
            }
        }

        binding.scheduleEditCancelBtn.setOnClickListener {
            finish()
        }
    }

    private suspend fun getTokenDB() : Token?{
        return tokenDB?.tokenDao()?.getAll()
    }

    private suspend fun getScheduleContent(eid : String, token : String){
        viewModel.getScheduleContent(eid, token)
    }

    private suspend fun editContent(eid : String, token: String){
        var title = binding.scheduleEditInputTitle.text
        var content = binding.scheduleEditInputContext.text

        //다른 변경 가능한 사항 뭔지?
        //val data = RetrofitPostScheduleDto(ScheduleDto(title.toString(), token.toString(), ))
        //viewModel.putScheduleContent(eid, token, )
    }
}