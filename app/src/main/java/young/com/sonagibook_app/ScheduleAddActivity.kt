package young.com.sonagibook_app

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import young.com.sonagibook_app.databinding.ActivityScheduleAddBinding
import young.com.sonagibook_app.retrofit.Dto.RetrofitPostScheduleDto
import young.com.sonagibook_app.retrofit.Dto.ScheduleDto
import young.com.sonagibook_app.room.TokenDatabase
import java.util.*

class ScheduleAddActivity : AppCompatActivity() {
    private val tokenDB by lazy { TokenDatabase.getInstance(this) }
    lateinit var binding: ActivityScheduleAddBinding
    lateinit var viewModel: ScheduleAddViewModel
    lateinit var viewModelFactory : ScheduleViewModelFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityScheduleAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModelFactory = ScheduleViewModelFactory(Repository())
        viewModel = ViewModelProvider(this,viewModelFactory)[ScheduleAddViewModel::class.java]


        binding.scheduleDatePicker1.setOnClickListener {
            val dialog = DateDialog(this)
            dialog.showDialog(Calendar.getInstance())
            dialog.setOnClickListener(object : DateDialog.onDialogClickListener{
                override fun onClicked(date: String) {
                    Toast.makeText(this@ScheduleAddActivity,date.toString(),Toast.LENGTH_LONG).show()
                }
            })
        }

        val data = RetrofitPostScheduleDto(ScheduleDto("일정 테스트", "일정 테스트입니다", "장소", "202211010900", "202211011000", null , 1, 1))

        binding.scheduleAddSendBtn.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                val token =
                    withContext(CoroutineScope(Dispatchers.IO).coroutineContext) { tokenDB?.tokenDao()?.getAll() }
                val accessToken = "Bearer ${token?.accessToken}"

                postSchedule(accessToken.toString(), data)
                Log.d(TAG, "onCreate: 일정 올리기 성공")
            }

        }



    }

    private suspend fun postSchedule(token : String, data : RetrofitPostScheduleDto){
        viewModel.postSchedule(token, data)
    }

}