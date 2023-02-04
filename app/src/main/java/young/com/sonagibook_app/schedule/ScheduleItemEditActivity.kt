package young.com.sonagibook_app.schedule

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import young.com.sonagibook_app.Repository
import young.com.sonagibook_app.databinding.ActivityScheduleItemEditBinding
import young.com.sonagibook_app.room.Token
import young.com.sonagibook_app.room.TokenDatabase

class ScheduleItemEditActivity : AppCompatActivity() {
    private val tokenDB by lazy { TokenDatabase.getInstance(applicationContext) }
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
            Log.d(TAG, "onCreate: ##$eid")
            viewModel.repositories1.observe(this@ScheduleItemEditActivity){
                Log.d(TAG, "onCreate: ##${it.data}")
                binding.scheduleEditInputTitle.setText(it.data.title.toString())
                binding.scheduleEditInputPlace.setText(it.data.content.toString())
            }

            binding.scheduleEditSendBtn.setOnClickListener {
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
        var content = binding.scheduleEditInputPlace.text

        //다른 변경 가능한 사항 뭔지?
        //val data = RetrofitPostScheduleDto(ScheduleDto(title.toString(), token.toString(), ))
        //viewModel.putScheduleContent(eid, token, )
    }
}