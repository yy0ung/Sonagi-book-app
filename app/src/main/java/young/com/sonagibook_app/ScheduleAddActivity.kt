package young.com.sonagibook_app

import android.annotation.SuppressLint
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
import java.text.DecimalFormat
import java.util.*
import kotlin.properties.Delegates

class ScheduleAddActivity : AppCompatActivity() {
    private val tokenDB by lazy { TokenDatabase.getInstance(this) }
    lateinit var binding: ActivityScheduleAddBinding
    lateinit var viewModel: ScheduleAddViewModel
    lateinit var viewModelFactory : ScheduleViewModelFactory
    private var pickedType by Delegates.notNull<Int>()
    private var startDate : String = "00000000"
    private var endDate : String = "00000000"
    lateinit var description : String
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityScheduleAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModelFactory = ScheduleViewModelFactory(Repository())
        viewModel = ViewModelProvider(this,viewModelFactory)[ScheduleAddViewModel::class.java]

        dateTimePick()
        selectTag()

        val today = Calendar.getInstance()
        binding.scheduleDatePicker1.text = "${today.get(Calendar.YEAR)}.${today.get(Calendar.MONTH)+1}.${today.get(Calendar.DAY_OF_MONTH)}"
        binding.scheduleDatePicker2.text = "${today.get(Calendar.YEAR)}.${today.get(Calendar.MONTH)+1}.${today.get(Calendar.DAY_OF_MONTH)}"
        if(today.get(Calendar.AM_PM)==0){
            binding.scheduleTimePicker1.text = "오전 ${today.get(Calendar.HOUR)}시 ${today.get(Calendar.MINUTE)}분"
            binding.scheduleTimePicker2.text = "오전 ${today.get(Calendar.HOUR)}시 ${today.get(Calendar.MINUTE)}분"
        }else{
            binding.scheduleTimePicker1.text = "오후 ${today.get(Calendar.HOUR)}시 ${today.get(Calendar.MINUTE)}분"
            binding.scheduleTimePicker2.text = "오후 ${today.get(Calendar.HOUR)}시 ${today.get(Calendar.MINUTE)}분"
        }




        val data = RetrofitPostScheduleDto(ScheduleDto("일정 테스트", "일정 테스트입니다", "장소", "202211090900", "202211091000", null, null,1))

        binding.scheduleAddSendBtn.setOnClickListener {
            addSchedule()

        }



    }

    private suspend fun postSchedule(token : String, data : RetrofitPostScheduleDto){
        viewModel.postSchedule(token, data)
    }

    private fun dateTimePick(){
        val cal = Calendar.getInstance()
        val format = DecimalFormat("00")
        binding.scheduleDatePicker1.setOnClickListener {
            val data1 = DatePickerDialog.OnDateSetListener{ view, year, month, date ->
                val m1 = format.format(month+1)
                val d1 = format.format(date)
                binding.scheduleDatePicker1.text = "$year/$m1/$d1"
                startDate = "$year$m1$d1"
            }
            DatePickerDialog(this, data1, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        binding.scheduleDatePicker2.setOnClickListener {
            val data2 = DatePickerDialog.OnDateSetListener{ view, year, month, date ->
                val m2 = format.format(month+1)
                val d2 = format.format(date)
                binding.scheduleDatePicker2.text = "$year/$m2/$d2"
                endDate = "$year$m2$d2"
            }
            DatePickerDialog(this, data2, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        binding.scheduleTimePicker1.setOnClickListener {
            val data2 = TimePickerDialog.OnTimeSetListener{ view, hourOfDay, minute ->
                val h1 = format.format(hourOfDay)
                val m1 = format.format(minute)
                binding.scheduleTimePicker1.text = "$h1 : $m1"
                startDate += "$h1$m1"
            }
            TimePickerDialog(this, data2, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }

        binding.scheduleTimePicker2.setOnClickListener {
            val data4 = TimePickerDialog.OnTimeSetListener{ view, hourOfDay, minute ->
                val h2 = format.format(hourOfDay)
                val m2 = format.format(minute)
                binding.scheduleTimePicker2.text = "$h2 : $m2"
                endDate+="$h2$m2"
            }
            TimePickerDialog(this, data4, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }


    }

    private fun selectTag(){
        pickedType = 1
    }

    private fun addSchedule(){
        val title = binding.scheduleAddInputTitle.text.toString()
        val place = binding.scheduleAddInputPlace.text.toString()
        val description = binding.scheduleAddInputDetail.text.toString()

        val data = RetrofitPostScheduleDto(ScheduleDto(title, description, place, startDate, endDate, null, null, pickedType))

        CoroutineScope(Dispatchers.Main).launch {
            val token =
                withContext(CoroutineScope(Dispatchers.IO).coroutineContext) { tokenDB?.tokenDao()?.getAll() }
            val accessToken = "Bearer ${token?.accessToken}"

            postSchedule(accessToken.toString(), data)
            Log.d(TAG, "데이터 $data")
        }
    }

}