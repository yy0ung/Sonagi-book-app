package young.com.sonagibook_app

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Color
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.format.ArrayWeekDayFormatter
import com.prolificinteractive.materialcalendarview.format.MonthArrayTitleFormatter
import kotlinx.coroutines.*
import okhttp3.internal.format
import young.com.sonagibook_app.retrofit.Dto.ScheduleDto
import young.com.sonagibook_app.retrofit.Dto.ScheduleResponseDto
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class ScheduleFragment : Fragment() {
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
        val view = inflater.inflate(R.layout.fragment_schedule, container, false)
        val addBtn : ImageView = view.findViewById(R.id.scheduleAddBtn)
        val detailTitle : TextView = view.findViewById(R.id.scheduleDetailTitle)
        val calHeader : TextView = view.findViewById(R.id.scheduleCalenderHeader)

        calHeader.text = "${selectedDate.year}??? ${selectedDate.month+1}???"
        detailTitle.text = "${selectedDate.day.toString()}??? ${setDayKorean(selectedDate.date.toString().substring(0,3))}"
        calendarView = view.findViewById(R.id.scheduleCalender)
        calendarView.setTitleFormatter(MonthArrayTitleFormatter(resources.getStringArray(R.array.custom_months)))
        calendarView.setWeekDayFormatter(ArrayWeekDayFormatter(resources.getStringArray(R.array.custom_weekdays)))
        calendarView.setHeaderTextAppearance(R.style.CalendarWidgetHeader)
        calendarView.topbarVisible = false

        calendarView.setOnMonthChangedListener { widget, date ->
            calHeader.text = "${date.year}??? ${date.month+1}???"
            //Log.d(TAG, "onCreateView: $$$$ ${date.calendar.add(1,1)}")
            (activity as MainActivity).getMonthSchedule(date.year.toString()+(format.format(date.month)).toString())
            Log.d(TAG, "##########?????? ${date.year.toString()+(format.format(date.month)).toString()}")
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
            Log.d(TAG, "onCreateView: ?????? ??????")
            Log.d(TAG, "onCreateView: ${viewModel.homeScheduleDataModel}")

            dotArray()
            //Log.d(TAG, "onCreateView: ??????")
            calendarView.addDecorator(EventDecorator(Color.parseColor("#A3E27B"), arr))

        }



        addBtn.setOnClickListener {
            val intent = Intent(context, ScheduleAddActivity::class.java)
            startActivity(intent)
        }



        return view
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
            val detailTitle : TextView = requireView().findViewById(R.id.scheduleDetailTitle)
            detailTitle.text = "${selectedDate.day.toString()}??? ${setDayKorean(selectedDate.date.toString().substring(0,3))}"
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

            Log.d(TAG, "onDateSelected: ?????? ?????? : $selectedDate")
            //dateString = dateString.substring(startDateString(dateString)+1, endDateString(dateString))
            Log.d(TAG, "setCalendar: ?????? : $dateString")
            Log.d(TAG, "setCalendar: ?????? ?????? : ${viewModel.homeScheduleDataModel[dateString]}")
            scheduleDetailList = viewModel.homeScheduleDataModel[dateString]
            val adapter = requireActivity().findViewById<RecyclerView>(R.id.scheduleDetailList)
            val detailAdapter = scheduleDetailList?.let { ScheduleListItemsAdapter(it) }
            adapter.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
            adapter.adapter = detailAdapter
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
            "Mon" -> wDay = "?????????"
            "Tue" -> wDay = "?????????"
            "Wed" -> wDay = "?????????"
            "Thu" -> wDay = "?????????"
            "Fri" -> wDay = "?????????"
            "Sat" -> wDay = "?????????"
            "Sun" -> wDay = "?????????"
        }
        return wDay.toString()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun dotArray(){
        for (i in viewModel.homeScheduleDataModel.keys){
            var fDate = LocalDate.parse(i, DateTimeFormatter.ISO_DATE)
            var c  = CalendarDay.from(fDate.year,fDate.monthValue-1,fDate.dayOfMonth)
            //Log.d(TAG, "dotArray: ??? $c")
            arr.add(c)
        }

        //Log.d(TAG, "dotArray: ?????? $arr")
    }

    

}