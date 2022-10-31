package young.com.sonagibook_app


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import young.com.sonagibook_app.retrofit.Dto.ScheduleDto

class ScheduleListItemsAdapter(private val scheduleItemList : ArrayList<ScheduleDto>)
    : RecyclerView.Adapter<ScheduleListItemsAdapter.CustomViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ScheduleListItemsAdapter.CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_schedule,parent,false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ScheduleListItemsAdapter.CustomViewHolder,
        position: Int
    ) {
        holder.itemTitle.text = scheduleItemList[position].title
        holder.itemPlace.text = scheduleItemList[position].place
        holder.itemTime.text = scheduleItemList[position].start
    }

    override fun getItemCount(): Int {
        return scheduleItemList.size
    }
    inner class CustomViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val itemContainer : ConstraintLayout = itemView.findViewById(R.id.scheduleItemContainer)
        val itemTitle : TextView = itemView.findViewById(R.id.scheduleItemTitle)
        val itemPlace : TextView = itemView.findViewById(R.id.scheduleItemPlace)
        val itemTime : TextView = itemView.findViewById(R.id.scheduleItemTime)
    }

}