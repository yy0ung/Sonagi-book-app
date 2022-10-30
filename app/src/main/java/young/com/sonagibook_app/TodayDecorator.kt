package young.com.sonagibook_app

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade

class TodayDecorator(context: Context) : DayViewDecorator {
    private var date = CalendarDay.today()
    val drawable = context.getDrawable(R.drawable.calendar_today)
    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return day?.equals(date)!!
    }

    override fun decorate(view: DayViewFacade?) {
        if(drawable!=null){
            view?.setSelectionDrawable(drawable)
        }else{
            Log.d(TAG, "decorate: null")
        }
    }

}