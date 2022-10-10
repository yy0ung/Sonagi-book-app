package young.com.sonagibook_app

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CalendarView
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.format.ArrayWeekDayFormatter
import com.prolificinteractive.materialcalendarview.format.MonthArrayTitleFormatter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.DayOfWeek
import java.util.*

class ScheduleFragment : Fragment() {
    private lateinit var viewModelFactory: MainViewModelFactory
    private lateinit var viewModel: MainViewModel
    lateinit var calendarView : MaterialCalendarView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_schedule, container, false)
        val addBtn : ImageView = view.findViewById(R.id.scheduleAddBtn)

        calendarView = view.findViewById(R.id.scheduleCalender)

        setCalendar(calendarView)

        viewModel = ViewModelProvider(requireActivity(), MainViewModelFactory(Repository())).get(
            MainViewModel::class.java)

        CoroutineScope(Dispatchers.Main).launch {
            fetchSchedule()
            Log.d(TAG, "onCreateView: 일정 요청")
            Log.d(TAG, "onCreateView: ${viewModel.homeScheduleDataModel[0]}")
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

    private fun setCalendar(cal : MaterialCalendarView){

        cal.setTitleFormatter(MonthArrayTitleFormatter(resources.getStringArray(R.array.custom_months)))
        cal.setWeekDayFormatter(ArrayWeekDayFormatter(resources.getStringArray(R.array.custom_weekdays)))


    }

}