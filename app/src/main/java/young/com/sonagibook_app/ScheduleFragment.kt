package young.com.sonagibook_app

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.format.ArrayWeekDayFormatter
import com.prolificinteractive.materialcalendarview.format.MonthArrayTitleFormatter
import kotlinx.coroutines.*
import young.com.sonagibook_app.retrofit.Dto.ScheduleDto
import java.util.*
import kotlin.collections.ArrayList

class ScheduleFragment : Fragment() {
    private lateinit var viewModelFactory: MainViewModelFactory
    private lateinit var viewModel: MainViewModel
    lateinit var calendarView : MaterialCalendarView
    var selectedDate : CalendarDay = CalendarDay.today()
    private var scheduleDetailList : ArrayList<ScheduleDto>? = ArrayList<ScheduleDto>()


    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_schedule, container, false)
        val addBtn : ImageView = view.findViewById(R.id.scheduleAddBtn)
        val detailTitle : TextView = view.findViewById(R.id.scheduleDetailTitle)

        var arr = ArrayList<CalendarDay>()
        arr.add(CalendarDay.today())


        detailTitle.text = "${selectedDate.day.toString()}일 ${selectedDate.date.toString().substring(0,3)}"
        calendarView = view.findViewById(R.id.scheduleCalender)
        calendarView.setTitleFormatter(MonthArrayTitleFormatter(resources.getStringArray(R.array.custom_months)))
        calendarView.setWeekDayFormatter(ArrayWeekDayFormatter(resources.getStringArray(R.array.custom_weekdays)))
        calendarView.setHeaderTextAppearance(R.style.CalendarWidgetHeader)
        //cal.addDecorators()
        val todayDecorator = context?.let { TodayDecorator(it) }
        calendarView.addDecorator(todayDecorator)
        calendarView.addDecorator(EventDecorator(Color.parseColor("#9AC1AA"), arr))

        setCalendar(calendarView)

        viewModel = ViewModelProvider(requireActivity(), MainViewModelFactory(Repository()))[MainViewModel::class.java]

        CoroutineScope(Dispatchers.Main).launch {
            fetchSchedule()
            Log.d(TAG, "onCreateView: 일정 요청")
            Log.d(TAG, "onCreateView: ${viewModel.homeScheduleDataModel}")

            for (i in viewModel.homeScheduleDataModel.keys){
                Log.d(TAG, "onCreateView: 날짜 $i")
            }

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
            detailTitle.text = "${selectedDate.day.toString()}일 ${selectedDate.date.toString().substring(0,3)}"
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

}