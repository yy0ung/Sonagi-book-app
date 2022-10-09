package young.com.sonagibook_app

import android.annotation.SuppressLint
import android.content.ContentValues
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

class NoticeListItemsAdapter(private val noticeItemList : ArrayList<RetrofitResponseNoticeDto>)
    : RecyclerView.Adapter<NoticeListItemsAdapter.CustomViewHolder>(), Filterable {

    //filter search
    var filteredNotice = java.util.ArrayList<RetrofitResponseNoticeDto>()
    var itemFilter = ItemFilter()
    init {
        filteredNotice.addAll(noticeItemList)
        Log.d(TAG, "시작 init: ")
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_notice,parent,false)

        return CustomViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.itemTitle.text = noticeItemList.get(0).data[position].title
        holder.itemUser.text = noticeItemList.get(0).data[position].name
        holder.itemLike.text = noticeItemList.get(0).data[position].likes.toString()

        //불러오기 다시
        if(noticeItemList.get(0).data[position].important==true){
            holder.itemImportant.visibility = View.GONE
        }
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
                holder.itemContent.text = content.substring(0,27)+"..."
            }
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
        val itemImportant : TextView = itemView.findViewById(R.id.noticeItemImportant)
    }

    inner class ItemFilter : Filter(){
        override fun performFiltering(char: CharSequence?): FilterResults {
            val filterString = char.toString()
            val results = FilterResults()
            Log.d(TAG, "performFiltering: 필터, $char")

            //원본
            val filteredList = ArrayList<RetrofitResponseNoticeDto>()
            //아무 입력이 없을 경우
            if(filterString.trim().isEmpty()){
                results.values = noticeItemList
                results.count = noticeItemList.get(0).data.size

                return results
            }else{
                for(notice in noticeItemList){
                    if((notice.data.get(0).title).contains(filterString) || ((notice.data.get(0).content)?.contains(filterString) ?: Boolean) as Boolean){
                        filteredList.add(notice)
                    }
                }
            }
            results.values = filteredList
            results.count = filteredList.get(0).data.size
            return results

        }

        @SuppressLint("NotifyDataSetChanged")
        override fun publishResults(char: CharSequence, p1: FilterResults) {
            filteredNotice.clear()
            filteredNotice.addAll(p1.values as java.util.ArrayList<RetrofitResponseNoticeDto>)
            notifyDataSetChanged()
        }
    }

    override fun getFilter(): Filter {
        return itemFilter
    }

}