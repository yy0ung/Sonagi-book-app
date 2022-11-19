package young.com.sonagibook_app


import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import young.com.sonagibook_app.retrofit.Dto.ScheduleDto
import young.com.sonagibook_app.retrofit.Dto.ScheduleResponseDto

class ScheduleListItemsAdapter(private val scheduleItemList : ArrayList<ScheduleResponseDto>)
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
        val startTime = scheduleItemList[position].start.substring(11,16)
        val endTime = scheduleItemList[position].end.substring(11,16)
        holder.itemTime.text = "$startTime\n~$endTime"

        when(scheduleItemList[position].type){
            0 -> {
                holder.itemType.setBackgroundResource(R.drawable.schedule_prac)
                holder.itemType.text = "연습"
            }
            1-> {
                holder.itemType.setBackgroundResource(R.drawable.schedule_event)
                holder.itemType.text = "행사"
            }
            2->{
                holder.itemType.setBackgroundResource(R.drawable.schedule_show)
                holder.itemType.text = "공연"
            }
            3->{
                holder.itemType.setBackgroundResource(R.drawable.schedule_performance)
                holder.itemType.text = "발표회"
            }
            4->{
                holder.itemType.setBackgroundResource(R.drawable.schedule_etc)
                holder.itemType.text = "기타"
            }
        }

        holder.itemContainer.setOnClickListener {
            val intent = Intent(holder.itemContainer.context,ScheduleContentActivity::class.java)
            intent.putExtra("eid", scheduleItemList[position].eid.toString())
            ContextCompat.startActivity(holder.itemContainer.context,intent,null)
        }
    }

    override fun getItemCount(): Int {
        return scheduleItemList.size
    }
    inner class CustomViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val itemContainer : ConstraintLayout = itemView.findViewById(R.id.scheduleItemContainer)
        val itemType : TextView = itemView.findViewById(R.id.scheduleContentType)
        val itemTitle : TextView = itemView.findViewById(R.id.scheduleItemTitle)
        val itemPlace : TextView = itemView.findViewById(R.id.scheduleItemPlaceText)
        val itemTime : TextView = itemView.findViewById(R.id.scheduleItemTime)
    }

}