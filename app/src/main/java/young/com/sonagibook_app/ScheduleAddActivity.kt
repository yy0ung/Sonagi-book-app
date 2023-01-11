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
import java.text.SimpleDateFormat
import java.util.*
import kotlin.properties.Delegates

class ScheduleAddActivity : AppCompatActivity() {
    private val tokenDB by lazy { TokenDatabase.getInstance(applicationContext) }
    lateinit var binding: ActivityScheduleAddBinding
    lateinit var viewModel: ScheduleAddViewModel
    lateinit var viewModelFactory : ScheduleViewModelFactory
    private var pickedType by Delegates.notNull<Int>()
    private var startDate : String = "00000000"
    private var endDate : String = "00000000"
    private lateinit var fStartDate : String
    private lateinit var fEndDate : String
    lateinit var description : String
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityScheduleAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModelFactory = ScheduleViewModelFactory(Repository())
        viewModel = ViewModelProvider(this,viewModelFactory)[ScheduleAddViewModel::class.java]

        //format check
//        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.KOREAN)
//        Log.d(TAG, "onCreate: get date ${sdf.format(Date())}")

        dateTimePick()
        selectTag()
        val format = DecimalFormat("00")
        val today = Calendar.getInstance()
        binding.scheduleDatePicker1.text = "${today.get(Calendar.YEAR)}.${format.format(today.get(Calendar.MONTH)+1)}.${format.format(today.get(Calendar.DAY_OF_MONTH))}"
        binding.scheduleDatePicker2.text = "${today.get(Calendar.YEAR)}.${format.format(today.get(Calendar.MONTH)+1)}.${format.format(today.get(Calendar.DAY_OF_MONTH))}"

//        startDate  = "${today.get(Calendar.YEAR)}${format.format(today.get(Calendar.MONTH)+1)}${format.format(today.get(Calendar.DAY_OF_MONTH))}" +
//                "${format.format(today.get(Calendar.HOUR))}${format.format(today.get(Calendar.MINUTE))}"
//        endDate = startDate

        Log.d(TAG, "onCreate: 초기값 $startDate")
        if(today.get(Calendar.AM_PM)==0){
            binding.scheduleTimePicker1.text = "오전 ${format.format(today.get(Calendar.HOUR))}시 ${format.format(today.get(Calendar.MINUTE))}분"
            binding.scheduleTimePicker2.text = "오전 ${format.format(today.get(Calendar.HOUR))}시 ${format.format(today.get(Calendar.MINUTE))}분"
        }else{
            binding.scheduleTimePicker1.text = "오후 ${format.format(today.get(Calendar.HOUR))}시 ${format.format(today.get(Calendar.MINUTE))}분"
            binding.scheduleTimePicker2.text = "오후 ${format.format(today.get(Calendar.HOUR))}시 ${format.format(today.get(Calendar.MINUTE))}분"
        }

        
        binding.scheduleAddSendBtn.setOnClickListener {
            startDate = binding.scheduleDatePicker1.text.toString()+binding.scheduleTimePicker1.text.toString()
            endDate = binding.scheduleDatePicker2.text.toString()+binding.scheduleTimePicker2.text.toString()
            fStartDate = setSubstring(startDate,0,4)+setSubstring(startDate,5,7)+setSubstring(startDate,8,10)+setSubstring(startDate,13,15)+setSubstring(startDate,17,19)
            fEndDate = setSubstring(endDate,0,4)+setSubstring(endDate,5,7)+setSubstring(endDate,8,10)+setSubstring(endDate,13,15)+setSubstring(endDate,17,19)

            if(fStartDate.toLong()<fEndDate.toLong()){
                addSchedule()
                Log.d(TAG, "YES: 시작 종료 $fStartDate $fEndDate")
            }else{
                Log.d(TAG, "NO 시작 종료 $fStartDate $fEndDate")
                Toast.makeText(this, "시작 일자와 종료 일자를 다시 확인하세요", Toast.LENGTH_LONG).show()
            }
            

        }



    }

    private suspend fun postSchedule(token : String, data : RetrofitPostScheduleDto){
        viewModel.postSchedule(token, data)
    }

    @SuppressLint("SetTextI18n")
    private fun dateTimePick(){
        val cal = Calendar.getInstance()
        val format = DecimalFormat("00")
        binding.scheduleDatePicker1.setOnClickListener {
            val data1 = DatePickerDialog.OnDateSetListener{ view, year, month, date ->
                val m1 = format.format(month+1)
                val d1 = format.format(date)
                binding.scheduleDatePicker1.text = "$year.$m1.$d1"
//                startDate = "$year$m1$d1"
            }
            DatePickerDialog(this, data1, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        binding.scheduleDatePicker2.setOnClickListener {
            val data2 = DatePickerDialog.OnDateSetListener{ view, year, month, date ->
                val m2 = format.format(month+1)
                val d2 = format.format(date)
                binding.scheduleDatePicker2.text = "$year.$m2.$d2"
//                endDate = "$year$m2$d2"
            }
            DatePickerDialog(this, data2, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        binding.scheduleTimePicker1.setOnClickListener {
            val data2 = TimePickerDialog.OnTimeSetListener{ view, hourOfDay, minute ->
                val h1 = format.format(hourOfDay)
                val m1 = format.format(minute)
                if(h1.toInt()<=12) {
                    binding.scheduleTimePicker1.text = "오전 ${h1}시 ${m1}분"
                }else{
                    binding.scheduleTimePicker1.text = "오후 ${format.format(h1.toInt()-12)}시 ${m1}분"
                }
//                startDate += "$h1$m1"
            }
            TimePickerDialog(this, data2, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()

        }

        binding.scheduleTimePicker2.setOnClickListener {
            val data4 = TimePickerDialog.OnTimeSetListener{ view, hourOfDay, minute ->
                val h2 = format.format(hourOfDay)
                val m2 = format.format(minute)
                if(h2.toInt()<=12) {
                    binding.scheduleTimePicker2.text = "오전 ${h2}시 ${m2}분"
                }else{
                    binding.scheduleTimePicker2.text = "오후 ${format.format(h2.toInt()-12)}시 ${m2}분"
                }
//                endDate+="$h2$m2"
            }
            TimePickerDialog(this, data4, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()

        }


    }

    private fun selectTag(){
        binding.scheduleT1.setOnClickListener {
            pickedType = 0
            binding.scheduleT1.setBackgroundResource(R.drawable.schedule_etc)
        }
        binding.scheduleT2.setOnClickListener {
            pickedType = 1
            binding.scheduleT2.setBackgroundResource(R.drawable.schedule_event)
        }
        pickedType = 1
        Log.d(TAG, "selectTag: $pickedType")
    }

    private fun addSchedule(){
        val title = binding.scheduleAddInputTitle.text.toString()
        val place = binding.scheduleAddInputPlace.text.toString()
        val description = binding.scheduleAddInputDetail.text.toString()

        val data = RetrofitPostScheduleDto(ScheduleDto(title, description, place, fStartDate, fEndDate, repeatDay = null, nid=null, pickedType))

        CoroutineScope(Dispatchers.Main).launch {
            val token =
                withContext(CoroutineScope(Dispatchers.IO).coroutineContext) { tokenDB?.tokenDao()?.getAll() }
            val accessToken = "Bearer ${token?.accessToken}"

            //유효성 test
            if(title.isEmpty() || description.isEmpty() || place.isEmpty()){
                Toast.makeText(this@ScheduleAddActivity, "필수 항목을 모두 채워주세요", Toast.LENGTH_LONG).show()
            }else{
                postSchedule(accessToken.toString(), data)
                Log.d(TAG, "데이터 $data")
            }
        }
    }

    private fun setSubstring(str : String, start : Int, end : Int) : String{
        return str.substring(start, end)
    }

}