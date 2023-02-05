package young.com.sonagibook_app.schedule

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.format.ArrayWeekDayFormatter
import com.prolificinteractive.materialcalendarview.format.MonthArrayTitleFormatter
import kotlinx.coroutines.*
import young.com.sonagibook_app.*
import young.com.sonagibook_app.databinding.FragmentScheduleBinding
import young.com.sonagibook_app.retrofit.Dto.ScheduleResponseDto
import java.text.DecimalFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class ScheduleFragment : Fragment() {
    private var _binding : FragmentScheduleBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModelFactory: MainViewModelFactory
    private lateinit var viewModel: MainViewModel
    lateinit var calendarView : MaterialCalendarView
    var selectedDate : CalendarDay = CalendarDay.today()
    private var scheduleDetailList : ArrayList<ScheduleResponseDto>? = ArrayList<ScheduleResponseDto>()
    var arr = ArrayList<CalendarDay>()
    private val format = DecimalFormat("00")

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentScheduleBinding.inflate(layoutInflater)
        val view = binding.root

        binding.scheduleCalenderHeader.text = "${selectedDate.year}년 ${selectedDate.month+1}월"
        binding.scheduleDetailTitle.text = "${selectedDate.day.toString()}일 ${setDayKorean(selectedDate.date.toString().substring(0,3))}"
//        calendarView = view.findViewById(R.id.scheduleCalender)
        calendarView = binding.scheduleCalender
        calendarView.setTitleFormatter(MonthArrayTitleFormatter(resources.getStringArray(R.array.custom_months)))
        calendarView.setWeekDayFormatter(ArrayWeekDayFormatter(resources.getStringArray(R.array.custom_weekdays)))
        calendarView.setHeaderTextAppearance(R.style.CalendarWidgetHeader)
        calendarView.topbarVisible = false

        calendarView.setOnMonthChangedListener { widget, date ->
            binding.scheduleCalenderHeader.text = "${date.year}년 ${date.month+1}월"
            //Log.d(TAG, "onCreateView: $$$$ ${date.calendar.add(1,1)}")
            (activity as MainActivity).getMonthSchedule(date.year.toString()+(format.format(date.month)).toString())
            Log.d(TAG, "##########쿼리 ${date.year.toString()+(format.format(date.month)).toString()}")
            CoroutineScope(Dispatchers.Main).launch {
                fetchSchedule()
                dotArray()
                calendarView.addDecorator(EventDecorator(Color.parseColor("#A3E27B"), arr))

            }

        }

        //cal.addDecorators()
        val todayDecorator = context?.let { TodayDecorator(it) }
        calendarView.addDecorator(todayDecorator)

        setCalendar(calendarView)

        viewModel = ViewModelProvider(requireActivity(), MainViewModelFactory(Repository()))[MainViewModel::class.java]

        CoroutineScope(Dispatchers.Main).launch {
            fetchSchedule()
            Log.d(TAG, "onCreateView: 일정 요청")
            Log.d(TAG, "onCreateView: ${viewModel.homeScheduleDataModel}")

            dotArray()
            //Log.d(TAG, "onCreateView: 순서")
            calendarView.addDecorator(EventDecorator(Color.parseColor("#A3E27B"), arr))

        }



        binding.scheduleAddBtn.setOnClickListener {
            val intent = Intent(context, ScheduleAddActivity::class.java)
            startActivity(intent)
        }



        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private suspend fun fetchSchedule(){
        withContext(Dispatchers.IO){
            while (viewModel.homeScheduleDataModel.size==0){}
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setCalendar(cal : MaterialCalendarView){
        cal.setOnDateChangedListener { widget, date, selected ->
            selectedDate = cal.selectedDate
            var aa = cal.selectedDate.month+1
            var date = cal.selectedDate.day
            binding.scheduleDetailTitle.text = "${selectedDate.day.toString()}일 ${setDayKorean(selectedDate.date.toString().substring(0,3))}"
            lateinit var dateString : String
            if(aa<10){
                if(date<10){
                    dateString = selectedDate.year.toString()+"-0"+aa+"-0"+selectedDate.day
                }else{
                    dateString = selectedDate.year.toString()+"-0"+aa+"-"+selectedDate.day
                }

            }else{
                if(date<10){
                    dateString = selectedDate.year.toString()+"-"+aa+"-0"+selectedDate.day
                }else{
                    dateString = selectedDate.year.toString()+"-"+aa+"-"+selectedDate.day
                }
            }

            Log.d(TAG, "onDateSelected: 선택 날짜 : $selectedDate")
            //dateString = dateString.substring(startDateString(dateString)+1, endDateString(dateString))
            Log.d(TAG, "setCalendar: 날짜 : $dateString")
            Log.d(TAG, "setCalendar: 선택 일정 : ${viewModel.homeScheduleDataModel[dateString]}")
            scheduleDetailList = viewModel.homeScheduleDataModel[dateString]
            val detailAdapter = scheduleDetailList?.let { ScheduleListItemsAdapter(it) }
            binding.scheduleDetailList.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
            binding.scheduleDetailList.adapter = detailAdapter
        }


    }

    private fun startDateString(st: String): Int {
        return st.indexOf("{")
    }

    private fun endDateString(st : String) : Int{
        return st.indexOf("}")
    }

    private fun setDayKorean(day : String) : String{
        var wDay : String?= null
        when(day){
            "Mon" -> wDay = "월요일"
            "Tue" -> wDay = "화요일"
            "Wed" -> wDay = "수요일"
            "Thu" -> wDay = "목요일"
            "Fri" -> wDay = "금요일"
            "Sat" -> wDay = "토요일"
            "Sun" -> wDay = "일요일"
        }
        return wDay.toString()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun dotArray(){
        for (i in viewModel.homeScheduleDataModel.keys){
            var fDate = LocalDate.parse(i, DateTimeFormatter.ISO_DATE)
            var c  = CalendarDay.from(fDate.year,fDate.monthValue-1,fDate.dayOfMonth)
            //Log.d(TAG, "dotArray: 점 $c")
            arr.add(c)
        }

        //Log.d(TAG, "dotArray: 배열 $arr")
    }

    

}