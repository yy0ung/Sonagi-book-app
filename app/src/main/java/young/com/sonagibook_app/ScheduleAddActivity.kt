package young.com.sonagibook_app

import android.app.DatePickerDialog
import android.app.TimePickerDialog
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
            val cal = Calendar.getInstance()
            val data1 = DatePickerDialog.OnDateSetListener{ view, year, month, date ->
                binding.scheduleDatePicker1.text = "$year/${month+1}/$date"
            }
            DatePickerDialog(this, data1, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        binding.scheduleTimePicker1.setOnClickListener {
            val cal = Calendar.getInstance()
            val data2 = TimePickerDialog.OnTimeSetListener{ view, hourOfDay, minute ->
                binding.scheduleTimePicker1.text = "$hourOfDay : $minute"
            }
            TimePickerDialog(this, data2, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }

        val data = RetrofitPostScheduleDto(ScheduleDto("일정 테스트", "일정 테스트입니다", "장소", "202211090900", "202211091000", null, null,1))

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