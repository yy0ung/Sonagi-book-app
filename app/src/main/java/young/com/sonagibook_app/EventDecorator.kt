package young.com.sonagibook_app

import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.spans.DotSpan

class EventDecorator(color : Int, event : ArrayList<CalendarDay>) : DayViewDecorator {
    private var event = event
    private var color = color
    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return event.contains(day)
    }

    override fun decorate(view: DayViewFacade?) {
        view?.addSpan(DotSpan(5F,color))
    }

}