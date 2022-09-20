package young.com.sonagibook_app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import young.com.sonagibook_app.retrofit.Dto.RetrofitResponseNoticeDto

class NoticeListItemsAdapter(private val noticeItemList : ArrayList<RetrofitResponseNoticeDto>)
    : RecyclerView.Adapter<NoticeListItemsAdapter.CustomViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_notice,parent,false)

        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.itemTitle.text = noticeItemList.get(0).data[position].title
        holder.itemUser.text = noticeItemList.get(0).data[position].name
    }

    override fun getItemCount(): Int {
        return noticeItemList.get(0).data.size
    }
    inner class CustomViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val itemTitle : TextView = itemView.findViewById(R.id.noticeItemTitle)
        val itemContent : TextView = itemView.findViewById(R.id.noticeItemContext)
        val itemUser : TextView = itemView.findViewById(R.id.noticeItemUser)
    }

}