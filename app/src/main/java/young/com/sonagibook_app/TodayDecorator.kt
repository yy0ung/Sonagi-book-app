package young.com.sonagibook_app

import android.content.ContentValues.TAG
import android.content.Context
import android.text.style.ForegroundColorSpan
import android.util.Log
import androidx.compose.ui.graphics.Color
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
        view?.addSpan(ForegroundColorSpan(android.graphics.Color.parseColor("#FFFFFF")))
        if(drawable!=null){
            view?.setSelectionDrawable(drawable)
        }else{
            Log.d(TAG, "decorate: null")
        }
    }

}