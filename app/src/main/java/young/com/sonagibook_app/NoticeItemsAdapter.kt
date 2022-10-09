package young.com.sonagibook_app

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import young.com.sonagibook_app.retrofit.Dto.RetrofitResponseNoticeDto

class NoticeItemsAdapter(private val noticeItemList : ArrayList<RetrofitResponseNoticeDto>)
    : RecyclerView.Adapter<NoticeItemsAdapter.CustomViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_notice,parent,false)

        return CustomViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.itemTitle.text = noticeItemList.get(0).data[position].title
        holder.itemUser.text = noticeItemList.get(0).data[position].name
        holder.itemLike.text = noticeItemList.get(0).data[position].likes.toString()
        holder.itemContainer.setOnClickListener {
            val intent = Intent(holder.itemContainer.context,NoticeContentActivity::class.java)
            intent.putExtra("nid",noticeItemList.get(0).data[position].nid.toString())
            ContextCompat.startActivity(holder.itemContainer.context,intent,null)
        }

        val content = noticeItemList.get(0).data[position].content
        if (content != null) {
            if(content.length <26){
                holder.itemContent.text = content.toString()
            }else{
                holder.itemContent.text = content.substring(0,27).toString()+"..."
            }
        }else{
            Log.d(TAG, "onBindViewHolder: 공지사항 내용 빈칸")
        }

        val createDate = noticeItemList.get(0).data[position].createdAt
        //val year = createDate.substring(0,4).toString()
        val month = createDate.substring(5,7).toString()
        val date = createDate.substring(8,10).toString()
        holder.itemDate.text = "${month}월 ${date}일"
    }

    override fun getItemCount(): Int {
        return noticeItemList.get(0).data.size
    }
    inner class CustomViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val itemContainer : ConstraintLayout = itemView.findViewById(R.id.noticeItemContainer)
        val itemTitle : TextView = itemView.findViewById(R.id.noticeItemTitle)
        val itemContent : TextView = itemView.findViewById(R.id.noticeItemContext)
        val itemUser : TextView = itemView.findViewById(R.id.noticeItemWriter)
        val itemLike : TextView = itemView.findViewById(R.id.noticeItemLikeNum)
        val itemDate : TextView = itemView.findViewById(R.id.noticeItemDate)
    }



}

